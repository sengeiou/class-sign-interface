package cn.fzn.classsign.common.service;

import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.entity.SignInStatistics;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("sign")
public interface SignFeignService {
    @RequestMapping(value = "listSignStatistics", method = RequestMethod.POST)
    public List<SignInStatistics> listSignStatistics(@RequestParam Integer cid);

    @RequestMapping(value = "listSignIn", method = RequestMethod.POST)
    public List<SignIn> listSignIn(@RequestParam Integer ssid);

    @RequestMapping(value = "createSign", method = RequestMethod.POST)
    public Integer createSign(@RequestBody SignInStatistics signInStatistics);

    @RequestMapping(value = "addSignRecord", method = RequestMethod.POST)
    public Boolean addSignRecord(@RequestBody List<SignIn> signInList);

    @RequestMapping(value = "getCidBySsid", method = RequestMethod.POST)
    public Integer getCidBySsid(@RequestParam Integer ssid);

    @RequestMapping(value = "updateSignInStatistics", method = RequestMethod.POST)
    public Boolean updateSignInStatistics(@RequestBody SignInStatistics signInStatistics);

    @RequestMapping(value = "getSignInStatisticsBySsid", method = RequestMethod.POST)
    public SignInStatistics getSignInStatisticsBySsid(@RequestParam Integer ssid);

    @RequestMapping(value = "increaseCheckInNum", method = RequestMethod.POST)
    public Boolean increaseCheckInNum(@RequestParam Integer ssid);

    @RequestMapping(value = "reduceCheckInNum", method = RequestMethod.POST)
    public Boolean reduceCheckInNum(@RequestParam Integer ssid);

    @RequestMapping(value = "listSignInRecord", method = RequestMethod.POST)
    public List<Map<String, Object>> listSignInRecord(@RequestParam Integer cid, @RequestParam Integer uid);

    @RequestMapping(value = "getSignInBySid", method = RequestMethod.POST)
    public SignIn getSignInBySid(@RequestParam Integer sid);

    @RequestMapping(value = "updateSignIn", method = RequestMethod.POST)
    public Boolean updateSignIn(@RequestBody SignIn signIn);
}
