package cn.fzn.classsign.teacher.controller;

import cn.fzn.classsign.common.constant.SignConstant;
import cn.fzn.classsign.common.entity.Class;
import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.entity.SignInStatistics;
import cn.fzn.classsign.common.response.ResponseResultJson;
import cn.fzn.classsign.teacher.service.ClassFeignClientService;
import cn.fzn.classsign.teacher.service.SignFeignClientService;
import cn.fzn.classsign.teacher.util.SignUtil;
import cn.fzn.classsign.teacher.util.TeacherRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("sign")
public class SignController {
    @Autowired
    private SignFeignClientService signService;
    @Autowired
    private ClassFeignClientService classService;
    @Autowired
    private TeacherRedisUtils teacherRedisUtils;
    @Autowired
    private SignUtil signUtil;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseResultJson<String> create(@RequestBody Map<String, Object> params) {
        Integer cid = Integer.parseInt(params.get("cid").toString());
        String name = params.get("name").toString();
        String longitude = params.get("longitude").toString();
        String latitude = params.get("latitude").toString();
        Integer seconds = Integer.parseInt(params.get("seconds").toString());
        try {
            Integer total = classService.getTotalByCid(cid);
            SignInStatistics signInStatistics = new SignInStatistics()
                    .setCid(cid)
                    .setName(name)
                    .setStartTime(System.currentTimeMillis())
                    .setCheckInNum(0)
                    .setTotal(total)
                    .setStatus(SignConstant.SIGN_PROCESSING);
            String signCode = null;
            do {
                signCode = signUtil.generateVerificationCode();
            } while (teacherRedisUtils.isSignCodeExist(signCode));
            Integer ssid = signService.createSign(signInStatistics);
            if (ssid != null) {
                teacherRedisUtils.createSignIn(ssid, cid, signCode, longitude, latitude, seconds);
                return ResponseResultJson.success("创建签到成功", signCode);
            }
            return ResponseResultJson.failed("数据库异常", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", null);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "stop", method = RequestMethod.POST)
    public ResponseResultJson<Boolean> stop(@RequestParam String signCode) {
        try {
            return ResponseResultJson.success("成功结算签到信息", signUtil.stopSignIn(signCode));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", Boolean.FALSE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseResultJson<Boolean> update(@RequestParam Integer sid, @RequestParam Integer ssid, @RequestParam String status) {
        try {
            SignIn signIn = signService.getSignInBySid(sid);
            if (status.equals(signIn.getStatus())) {
                return ResponseResultJson.success("成功修改签到状态", Boolean.TRUE);
            }
            //原来缺席，现在不缺席
            if (SignConstant.SIGN_ABSENCE.equals(signIn.getStatus()) && !status.equals(SignConstant.SIGN_ABSENCE)) {
                signService.increaseCheckInNum(ssid);
            }
            //原来不缺席，现在缺席
            if (!SignConstant.SIGN_ABSENCE.equals(status) && SignConstant.SIGN_ABSENCE.equals(status)) {
                signService.reduceCheckInNum(ssid);
            }
            return ResponseResultJson
                    .success("成功结算签到信息",
                            signService.updateSignIn(
                                    new SignIn()
                                            .setSid(sid)
                                            .setStatus(status)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", Boolean.FALSE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "getCurrentSignTotal", method = RequestMethod.POST)
    public ResponseResultJson<Integer> getCurrentSignTotal(@RequestParam String signCode) {
        try {
            //该2是cid和ssid占得位置
            Integer total = teacherRedisUtils.getCurrentSignTotal(signCode) - 2;
            return ResponseResultJson.success("成功获取签到人数", total);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", 0);
        }
    }
}
