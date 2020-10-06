package cn.fzn.classsign.sms.util;

import cn.fzn.classsign.common.constant.SmsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 与充电桩redis的有关操作
 */
@Component
public class UserRedisUtils {
    @Autowired
    private RedisUtil redisUtil;

    public String getRegisterCode(String phone){
        String code=SmsUtil.generateVerificationCode();
        try{
            redisUtil.set(SmsConstant.Type.REGISTER_TEMPLATE + phone, code,SmsConstant.SMS_SECOND);
            return code;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String getLoginCode(String phone){
        String code=SmsUtil.generateVerificationCode();
        try{
            redisUtil.set(SmsConstant.Type.LOGIN_TEMPLATE + phone, code,SmsConstant.SMS_SECOND);
            return code;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
