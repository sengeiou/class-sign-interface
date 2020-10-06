package cn.fzn.classsign.sms.controller;


import cn.fzn.classsign.common.constant.SmsConstant;
import cn.fzn.classsign.common.response.ResponseResultJson;
import cn.fzn.classsign.sms.service.UserFeignClientService;
import cn.fzn.classsign.sms.util.UserRedisUtils;
import cn.fzn.classsign.sms.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qh256
 */
@RestController
public class SmsController {
    @Autowired
    private UserFeignClientService userService;
    @Autowired
    private UserRedisUtils userRedisUtils;

    @RequestMapping(value = "/sendRegisterCode/{phone}", method = RequestMethod.GET)
    public ResponseResultJson<Boolean> sendRegisterCode(@PathVariable("phone") String phone) {
        try {
            Integer uid = userService.getUidByPhone(phone);
            if (uid != null) {
                System.out.println(uid);
                return ResponseResultJson.failed(1, "该手机号已被注册", Boolean.FALSE);
            }
            String codeString = userRedisUtils.getRegisterCode(phone);
            if (codeString == null) {
                return ResponseResultJson.failed(2, "缓存异常", Boolean.FALSE);
            }
            if (SmsUtil.SendSms(phone, codeString, SmsConstant.Code.REGISTER_TEMPLATE)) {
                return ResponseResultJson.success("成功发送验证码", Boolean.TRUE);
            } else {
                return ResponseResultJson.failed(3, "验证码发送异常", Boolean.FALSE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知异常", Boolean.FALSE);
        }
    }

    @RequestMapping(value = "/sendLoginCode/{phone}", method = RequestMethod.GET)
    public ResponseResultJson<Boolean> sendLoginCode(@PathVariable("phone") String phone) {
        try {
            if (userService.getUidByPhone(phone) == null) {
                return ResponseResultJson.failed(1, "没有该用户", Boolean.FALSE);
            }
            String codeString = userRedisUtils.getLoginCode(phone);
            if (codeString == null) {
                return ResponseResultJson.failed(2, "缓存异常", Boolean.FALSE);
            }
            if (SmsUtil.SendSms(phone, codeString, SmsConstant.Code.LOGIN_TEMPLATE)) {
                return ResponseResultJson.success("成功发送验证码", Boolean.TRUE);
            } else {
                return ResponseResultJson.failed(3, "验证码发送异常", Boolean.FALSE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知异常", Boolean.FALSE);
        }
    }
}
