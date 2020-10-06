package cn.fzn.classsign.teacher.util;

import cn.fzn.classsign.common.constant.SmsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 与充电桩redis的有关操作
 */
@Component
public class TeacherRedisUtils {
    @Autowired
    private RedisUtil redisUtil;

    public Boolean createClassJoinCodeCache(String joinCode, Integer cid) {
        try {
            redisUtil.set("class-" + joinCode, cid);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public Boolean createSignIn(Integer ssid, Integer cid, String signCode, String longitude, String latitude, Integer seconds) {
        try {
            Map<Object, Object> signInfo = new HashMap<>();
            signInfo.put("cid", cid.toString());
            signInfo.put("ssid", ssid.toString());
            redisUtil.set("s-" + signCode, longitude + "-" + latitude + "-" + ssid + "-" + cid, seconds);
            redisUtil.hmset("sign-" + signCode, signInfo);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public Integer getCurrentSignTotal(String signCode) {
        try {
            Map<Object, Object> map = redisUtil.hmget("sign-" + signCode);
            if (map == null) {
                return 0;
            }
            Integer signTotal = map.size();
            return signTotal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean isSignCodeExist(String signCode) {
        try {
            if (StringUtils.isEmpty(signCode)) {
                return Boolean.FALSE;
            }
            return redisUtil.hasKey("sign-" + signCode);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public Map<Object, Object> listSignStudents(String key) {
        try {
            Map<Object, Object> map = redisUtil.hmget(key);
            redisUtil.del(key);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<String> getSignProcessing(String key) {
        try {
            String signInfo = redisUtil.get(key).toString();
            return Arrays.asList(signInfo.split("-"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean delSign(String key) {
        try {
            redisUtil.del(key);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
