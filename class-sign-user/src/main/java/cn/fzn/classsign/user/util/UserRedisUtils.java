package cn.fzn.classsign.user.util;

import cn.fzn.classsign.common.constant.SmsConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 与充电桩redis的有关操作
 */
@Slf4j
@Component
public class UserRedisUtils {
    @Autowired
    private RedisUtil redisUtil;

    public Boolean isLoginCodeRight(String phone, String code) {
        try {
            Boolean result = code.equals(redisUtil.get(SmsConstant.Type.LOGIN_TEMPLATE + phone));
            redisUtil.del(SmsConstant.Type.LOGIN_TEMPLATE + phone);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean isRegisterCodeRight(String phone, String code) {
        try {
            Object obj = redisUtil.get(SmsConstant.Type.REGISTER_TEMPLATE + phone);
            if (obj == null) {
                return Boolean.FALSE;
            }
            Boolean result = code.equals(obj.toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean isRegisterCodeRightAndDel(String phone, String code) {
        try {
            Boolean result = code.equals(redisUtil.get(SmsConstant.Type.REGISTER_TEMPLATE + phone));
            redisUtil.del(SmsConstant.Type.REGISTER_TEMPLATE + phone);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean putUserInfo(Integer uid, String uNum, String name) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uNum", uNum);
            map.put("name", name);
            redisUtil.hmset("user-" + uid, map, 60 * 60 * 2);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
