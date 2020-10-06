package cn.fzn.classsign.student.util;

import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 与充电桩redis的有关操作
 */
@Component
public class StudentRedisUtils {
    @Autowired
    private RedisUtils redisUtils;

    public Integer getClassId(String joinCode) {
        try {
            return Integer.parseInt(redisUtils.get("class-" + joinCode).toString());
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getSignInfo(String key) {
        try {
            String[] locationStr = redisUtils.get(key).toString().split("-");
            return Arrays.stream(locationStr)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserInfo(Integer uid) {
        try {
            Map<Object, Object> map = redisUtils.hmget("user-" + uid.toString());
            User user = new User()
                    .setUid(uid);
            String uNum = map.get("uNum").toString();
            String name = map.get("name").toString();
            if (uNum != null) {
                user.setUNum(uNum);
            }
            if (name != null) {
                user.setName(name);
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Boolean signIn(SignIn signIn, String joinCode) {
        try {
            return redisUtils.hset("sign-" + joinCode, signIn.getUid().toString(), signIn.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSignProcessing(String signCode) {
        try {
            return redisUtils.hasKey("s-" + signCode);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
