package cn.fzn.classsign.cinterface.controller;

import cn.fzn.classsign.cinterface.service.UserFeignClientService;
import cn.fzn.classsign.common.entity.User;
import cn.fzn.classsign.common.response.ResponseResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserFeignClientService userService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public ResponseResultJson<Boolean> updatePassword(@RequestBody Map<String, String> params) {
        String oldPassword = params.get("oldPassword");
        String newPasswrod = params.get("newPassword");
        if (oldPassword.equals(newPasswrod)) {
            return ResponseResultJson.failed("新旧密码相同", Boolean.FALSE);
        }
        if (StringUtils.isEmpty(newPasswrod)) {
            return ResponseResultJson.failed("新密码为空", Boolean.FALSE);
        }
        Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        User user = userService.getUserByUid(uid);
        if (!new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            return ResponseResultJson.failed("旧密码输入错误", Boolean.FALSE);
        }
        try {
            String encodePassword = new BCryptPasswordEncoder().encode(newPasswrod);
            userService.updatePasswordByUid(uid, encodePassword);
            return ResponseResultJson.success("修改密码成功", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", Boolean.FALSE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "updateUserBaseInfo", method = RequestMethod.POST)
    public ResponseResultJson<Boolean> updateUserBaseInfo(@RequestBody Map<String, String> params) {
        String uNum = params.get("uNum");
        String name = params.get("name");
        String sex = params.get("sex");
        Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        User user = new User()
                .setUid(uid)
                .setUNum(uNum)
                .setName(name)
                .setSex(sex);
        try {
            userService.updateUser(user);
            return ResponseResultJson.success("修改用户信息成功", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", Boolean.FALSE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "getUserBaseInfo", method = RequestMethod.GET)
    public ResponseResultJson<User> getUserBaseInfo() {
        Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        try {
            User user = userService.getUserByUid(uid);
            user.setPassword(null);
            return ResponseResultJson.success("获取用户信息成功", user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", null);
        }
    }
}
