package cn.fzn.classsign.user.controller;


import cn.fzn.classsign.common.entity.User;
import cn.fzn.classsign.common.response.ResponseResultJson;
import cn.fzn.classsign.user.config.resourceconfig.RequestTokenConfig;
import cn.fzn.classsign.user.service.UserFeignClientService;
import cn.fzn.classsign.user.util.UserRedisUtils;
import com.alibaba.fastjson.JSONObject;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UaaController {

    @Autowired
    private SpringClientFactory factory;
    @Autowired
    private RequestTokenConfig requestTokenConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserFeignClientService userService;
    @Autowired
    private UserRedisUtils userRedisUtils;

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseResultJson<Map<String, String>> login(@RequestBody Map<String, String> auth) {
        String phone = auth.get("phone");
        String password = auth.get("password");
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return ResponseResultJson.failed("授权失败,用户名或密码为空", null);
        }
        User user = userService.getUserByPhone(phone);
        Integer uid = null;
        String uNum = null;
        String name = null;
        try {
            uid = user.getUid();
            uNum = user.getUNum();
            name = user.getName();
        } catch (Exception e) {
            return ResponseResultJson.failed("没有该用户:", null);
        }
        //令牌获取
        requestTokenConfig.setUsername(uid.toString());
        requestTokenConfig.setPassword(password);
        Server server;
        String uaaUrl;
        try {
            server = factory.getLoadBalancer("SERVICE-UAA").chooseServer(null);
            uaaUrl = "http://" + server.getHostPort() + "/oauth/token";
        } catch (Exception e) {
            return ResponseResultJson.unknownError(101, "无法获取授权服务:" + e.getMessage(), null);
        }
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", requestTokenConfig.getCLIENT_ID());
        map.add("client_secret", requestTokenConfig.getCLIENT_SECRET());
        map.add("grant_type", requestTokenConfig.getLOGIN_GRANT_TYPE());
        map.add("username", requestTokenConfig.getUsername());
        map.add("password", requestTokenConfig.getPassword());
        String result;
        try {
            result = restTemplate.postForObject(uaaUrl, map, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseResultJson.failed(101, "授权失败：" + e.getMessage(), null);
        }

        //格式化结果
        JSONObject jsonObject = JSONObject.parseObject(result);
        Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
        try {
            if (jsonObject.get("uid") != null) {
                userRedisUtils.putUserInfo(uid, uNum, name);
                return ResponseResultJson.success(100, "授权成功", resultMap);
            } else {
                return ResponseResultJson.failed(101, "授权失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed(101, "授权失败", null);
        }

    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "refresh", method = RequestMethod.POST)
    public ResponseResultJson<Map<String, String>> refresh(@RequestBody Map<String, String> token) {
        String refreshToken = token.get("token");
        if (refreshToken == null) {
            return ResponseResultJson.failed(101, "授权失败", null);
        }
        Server server = factory.getLoadBalancer("SERVICE-UAA").chooseServer(null);
        String uaaUrl = "http://" + server.getHostPort() + "/oauth/token";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", requestTokenConfig.getCLIENT_ID());
        map.add("client_secret", requestTokenConfig.getCLIENT_SECRET());
        map.add("grant_type", requestTokenConfig.getREFRESH_GRANT_TYPE());
        map.add("refresh_token", refreshToken);
        String result;
        try {
            result = restTemplate.postForObject(uaaUrl, map, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseResultJson.failed(101, "授权失败：" + e.getMessage(), null);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
        try {
            if (jsonObject.get("uid") != null) {
                User user = userService.getUserByUid(Integer.parseInt(jsonObject.get("uid").toString()));
                userRedisUtils.putUserInfo(user.getUid(), user.getUNum(), user.getName());
                return ResponseResultJson.success("令牌刷新成功", resultMap);
            } else {
                return ResponseResultJson.failed("令牌刷新失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed(101, "授权失败", null);
        }
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "signUp", method = RequestMethod.POST)
    public ResponseResultJson<Boolean> signUp(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String code = params.get("code");
        String password = params.get("password");
        try {
            if (!userRedisUtils.isRegisterCodeRightAndDel(phone, code)) {
                return ResponseResultJson.failed(1, "验证码不正确", null);
            }
            User user = new User()
                    .setUNum(phone)
                    .setName(phone)
                    .setSex("male")
                    .setPhone(phone)
                    .setPassword(new BCryptPasswordEncoder().encode(password));
            if (!userService.addUser(user)) {
                return ResponseResultJson.failed("用户创建失败", null);
            }
            return ResponseResultJson.success("用户创建成功", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", null);
        }
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "isRegisterCodeRight", method = RequestMethod.POST)
    public ResponseResultJson<Boolean> isRegisterCodeRight(@RequestBody Map<String, String> params) {
        try {
            String phone = params.get("phone");
            String code = params.get("code");
            if (!userRedisUtils.isRegisterCodeRight(phone, code)) {
                return ResponseResultJson.failed("验证码不正确", Boolean.FALSE);
            }
            return ResponseResultJson.success("验证码正确", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", Boolean.FALSE);
        }
    }
}
