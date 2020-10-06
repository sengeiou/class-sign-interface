package cn.fzn.classsign.student.util;

import cn.fzn.classsign.common.constant.SignConstant;
import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.service.ClassFeignService;
import cn.fzn.classsign.student.service.ClassFeignClientService;
import cn.fzn.classsign.student.service.SignFeignClientService;
import cn.fzn.classsign.student.service.UserFeignClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SignUtils {
    @Autowired
    private StudentRedisUtils studentRedisUtils;
    @Autowired
    private ClassFeignService classService;


    public Boolean isSignRight(Double longitude, Double latitude, List<String> objects) {
        try {
            Double lgd = Double.parseDouble(objects.get(0));
            Double ltd = Double.parseDouble(objects.get(1));
            double distance = LocationUtils.getDistance(longitude, latitude, lgd, ltd);
            if (distance > SignConstant.SIGN_MAX_DISTANCE) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }

    }

    public boolean isStuInClass(Integer uid, List<String> objects) {
        try {
            log.info(String.valueOf(objects));
            Integer cid = Integer.parseInt(objects.get(3));
            if (classService.isStudentExistInClass(cid, uid) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
