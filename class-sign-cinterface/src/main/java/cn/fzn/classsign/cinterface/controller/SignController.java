package cn.fzn.classsign.cinterface.controller;

import cn.fzn.classsign.cinterface.service.ClassFeignClientService;
import cn.fzn.classsign.cinterface.service.SignFeignClientService;
import cn.fzn.classsign.common.entity.Class;
import cn.fzn.classsign.common.entity.SignIn;
import cn.fzn.classsign.common.entity.SignInStatistics;
import cn.fzn.classsign.common.response.ResponseResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sign")
public class SignController {
    @Autowired
    private SignFeignClientService signService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "listSignStatistics/{cid}", method = RequestMethod.GET)
    public ResponseResultJson<List<SignInStatistics>> listSignStatistics(@PathVariable("cid") Integer cid) {
        try {
            List<SignInStatistics> signInStatisticsList = signService.listSignStatistics(cid);
            return ResponseResultJson.success("获取签到统计结果成功", signInStatisticsList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("获取签到统计结果失败", null);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "listSignIn/{ssid}", method = RequestMethod.GET)
    public ResponseResultJson<List<SignIn>> listSignIn(@PathVariable("ssid") Integer ssid) {
        try {
            List<SignIn> signInList = signService.listSignIn(ssid);
            return ResponseResultJson.success("获取签到统计结果成功", signInList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("获取签到统计结果失败", null);
        }
    }

}
