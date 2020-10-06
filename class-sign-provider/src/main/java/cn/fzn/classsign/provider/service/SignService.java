package cn.fzn.classsign.provider.service;

import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.entity.SignInStatistics;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface SignService {
    List<SignInStatistics> listSignStatistics(Integer cid);

    List<SignIn> listSignIn(Integer ssid);

    Integer createSign(SignInStatistics signInStatistics);

    Boolean addSignRecord(List<SignIn> signInList);

    Integer getCidBySsid(Integer ssid);

    Boolean updateSignInStatistics(SignInStatistics signInStatistics);

    SignInStatistics getSignInStatisticsBySsid(Integer ssid);

    Boolean increaseCheckInNum(Integer ssid);

    Boolean reduceCheckInNum(Integer ssid);

    List<Map<String, Object>> listSignInRecord(Integer cid, Integer uid);

    SignIn getSignInBySid(Integer sid);

    Boolean updateSignIn(SignIn signIn);
}
