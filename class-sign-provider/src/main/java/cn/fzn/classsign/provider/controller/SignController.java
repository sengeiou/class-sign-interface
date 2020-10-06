package cn.fzn.classsign.provider.controller;

import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.entity.SignInStatistics;
import cn.fzn.classsign.common.service.SignFeignService;
import cn.fzn.classsign.provider.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
public class SignController implements SignFeignService {
    @Autowired
    private SignService signService;
    @Override
    public List<SignInStatistics> listSignStatistics(Integer cid) {
        return signService.listSignStatistics(cid);
    }

    @Override
    public List<SignIn> listSignIn(Integer ssid) {
        return signService.listSignIn(ssid);
    }

    @Override
    public Integer createSign(SignInStatistics signInStatistics) {
        return signService.createSign(signInStatistics);
    }

    @Override
    public Boolean addSignRecord(List<SignIn> signInList) {
        return signService.addSignRecord(signInList);
    }

    @Override
    public Integer getCidBySsid(Integer ssid) {
        return signService.getCidBySsid(ssid);
    }

    @Override
    public Boolean updateSignInStatistics(SignInStatistics signInStatistics) {
        return signService.updateSignInStatistics(signInStatistics);
    }

    @Override
    public SignInStatistics getSignInStatisticsBySsid(Integer ssid) {
        return signService.getSignInStatisticsBySsid(ssid);
    }

    @Override
    public Boolean increaseCheckInNum(Integer ssid) {
        return signService.increaseCheckInNum(ssid);
    }

    @Override
    public Boolean reduceCheckInNum(Integer ssid) {
        return signService.reduceCheckInNum(ssid);
    }

    @Override
    public List<Map<String, Object>> listSignInRecord(Integer cid, Integer uid) {
        return signService.listSignInRecord(cid,uid);
    }

    @Override
    public SignIn getSignInBySid(Integer sid) {
        return signService.getSignInBySid(sid);
    }

    @Override
    public Boolean updateSignIn(SignIn signIn) {
        return signService.updateSignIn(signIn);
    }
}
