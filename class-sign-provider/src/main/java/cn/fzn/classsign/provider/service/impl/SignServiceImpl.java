package cn.fzn.classsign.provider.service.impl;

import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.entity.SignInStatistics;
import cn.fzn.classsign.provider.dao.SignDao;
import cn.fzn.classsign.provider.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SignServiceImpl implements SignService {
    @Autowired
    private SignDao signDao;

    @Override
    public List<SignInStatistics> listSignStatistics(Integer cid) {
        return signDao.listSignStatistics(cid);
    }

    @Override
    public List<SignIn> listSignIn(Integer ssid) {
        return signDao.listSignIn(ssid);
    }

    @Override
    public Integer createSign(SignInStatistics signInStatistics) {
        signDao.createSign(signInStatistics);
        return signInStatistics.getSsid();
    }

    @Override
    public Boolean addSignRecord(List<SignIn> signInList) {
        return signDao.addSignRecord(signInList);
    }

    @Override
    public Integer getCidBySsid(Integer ssid) {
        return signDao.getCidBySsid(ssid);
    }

    @Override
    public Boolean updateSignInStatistics(SignInStatistics signInStatistics) {
        return signDao.updateSignInStatistics(signInStatistics);
    }

    @Override
    public SignInStatistics getSignInStatisticsBySsid(Integer ssid) {
        return signDao.getSignInStatisticsBySsid(ssid);
    }

    @Override
    public Boolean increaseCheckInNum(Integer ssid) {
        return signDao.increaseCheckInNum(ssid);
    }

    @Override
    public Boolean reduceCheckInNum(Integer ssid) {
        return signDao.reduceCheckInNum(ssid);
    }

    @Override
    public List<Map<String, Object>> listSignInRecord(Integer cid, Integer uid) {
        return signDao.listSignInRecord(cid, uid);
    }

    @Override
    public SignIn getSignInBySid(Integer sid) {
        return signDao.getSignInBySid(sid);
    }

    @Override
    public Boolean updateSignIn(SignIn signIn) {
        return signDao.updateSignIn(signIn);
    }
}
