package cn.fzn.classsign.teacher.util;

import cn.fzn.classsign.common.constant.SignConstant;
import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.entity.SignInStatistics;
import cn.fzn.classsign.common.entity.User;
import cn.fzn.classsign.teacher.service.ClassFeignClientService;
import cn.fzn.classsign.teacher.service.SignFeignClientService;
import cn.fzn.classsign.teacher.service.UserFeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SignUtil {
    @Autowired
    private TeacherRedisUtils teacherRedisUtils;
    @Autowired
    private ClassFeignClientService classService;
    @Autowired
    private SignFeignClientService signService;

    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder codeString = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            codeString.append(random.nextInt(10));
        }
        return codeString.toString();
    }

    public Boolean stopSignIn(String signCode) {
        try {
            String signKey = "s-" + signCode;
            teacherRedisUtils.delSign(signKey);
            Map<Object, Object> students = teacherRedisUtils.listSignStudents("sign-" + signCode);
            if (students == null) {
                return Boolean.FALSE;
            } else {
                teacherRedisUtils.delSign("sign-" + signCode);
            }
            Integer cid = Integer.parseInt(students.get("cid").toString());
            Integer ssid = Integer.parseInt(students.get("ssid").toString());
            List<User> allStudentList = classService.listStudents(cid);
            List<SignIn> signInList = new ArrayList<>();
            Integer checkInNum = 0;
            for (User user : allStudentList) {
                SignIn signIn = new SignIn()
                        .setSsid(ssid)
                        .setUid(user.getUid())
                        .setUNum(user.getUNum())
                        .setName(user.getName());
                String uid = user.getUid().toString();
                //没有签到的
                if (!students.containsKey(uid)) {
                    signIn.setStatus(SignConstant.SIGN_ABSENCE);
                } else {
                    signIn.setStatus(students.get(uid).toString());
                    checkInNum++;
                }
                signInList.add(signIn);
            }
            if (signInList.size() != 0) {
                signService.addSignRecord(signInList);
            }
            SignInStatistics signInStatistics = new SignInStatistics()
                    .setSsid(ssid)
                    .setCheckInNum(checkInNum)
                    .setStatus(SignConstant.SIGN_OVER);
            signService.updateSignInStatistics(signInStatistics);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }


}
