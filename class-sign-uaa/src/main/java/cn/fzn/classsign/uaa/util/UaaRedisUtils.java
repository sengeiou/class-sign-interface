package cn.fzn.classsign.uaa.util;

import cn.fzn.classsign.common.constant.SmsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 与充电桩redis的有关操作
 */
@Component
public class UaaRedisUtils {
    @Autowired
    private RedisUtil redisUtil;

    public Boolean isCodeLogin(String phone, String loginCode) {
        try {
            String key = SmsConstant.Type.LOGIN_TEMPLATE + phone;
            Object temp = redisUtil.get(key);
            if (temp == null) {
                return false;
            }
            String redisLoginCode = temp.toString();
            if (loginCode.equals(redisLoginCode)) {
                redisUtil.del(key);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
