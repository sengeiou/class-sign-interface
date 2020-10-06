package cn.fzn.classsign.student.controller;

import cn.fzn.classsign.common.constant.SignConstant;
import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.entity.User;
import cn.fzn.classsign.common.response.ResponseResultJson;
import cn.fzn.classsign.student.service.SignFeignClientService;
import cn.fzn.classsign.student.util.SignUtils;
import cn.fzn.classsign.student.util.StudentRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sign")
public class SignController {
    @Autowired
    private SignFeignClientService signService;
    @Autowired
    private SignUtils signUtils;
    @Autowired
    private StudentRedisUtils studentRedisUtils;


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public ResponseResultJson<Boolean> signin(@RequestBody Map<String, Object> params) {
        try {
            Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            User user = studentRedisUtils.getUserInfo(uid);
            if (user == null) {
                return ResponseResultJson.failed(204, "未知错误", Boolean.FALSE);
            }
            String signCode = params.get("signCode").toString();
            List<String> objects = studentRedisUtils.getSignInfo("s-" + signCode);
            if (!signUtils.isStuInClass(uid, objects)) {
                return ResponseResultJson.failed(205, "该学生不属于该课堂", Boolean.FALSE);
            }
            if (!studentRedisUtils.isSignProcessing(signCode)) {
                return ResponseResultJson.failed(203, "没有该签到", Boolean.FALSE);
            }
            Double longitude = Double.parseDouble(params.get("longitude").toString());
            Double latitude = Double.parseDouble(params.get("latitude").toString());

            SignIn signIn = new SignIn()
                    .setUid(uid)
                    .setUNum(user.getUNum())
                    .setName(user.getName());
            Boolean isRight = signUtils.isSignRight(longitude, latitude, objects);

            if (isRight) {
                signIn.setStatus(SignConstant.SIGN_NORMAL);
            } else {
                signIn.setStatus(SignConstant.SIGN_ABNORMAL);
            }
            if (studentRedisUtils.signIn(signIn, signCode)) {
                if (isRight) {
                    return ResponseResultJson.success(201, "签到成功", Boolean.TRUE);
                } else {
                    return ResponseResultJson.success(202, "异地签到", Boolean.TRUE);
                }
            }
            return ResponseResultJson.failed(204, "签到失败", Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed(204, "未知错误", Boolean.FALSE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "listSignInRecord/{cid}", method = RequestMethod.GET)
    public ResponseResultJson<List<Map<String, Object>>> listSignInRecord(@PathVariable("cid") Integer cid) {
        try {
            Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            List<Map<String, Object>> list = signService.listSignInRecord(cid, uid);
            return ResponseResultJson.success("获取学生签到记录成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", null);
        }
    }
}
