package cn.fzn.classsign.cinterface.controller;

import cn.fzn.classsign.cinterface.service.ClassFeignClientService;
import cn.fzn.classsign.common.entity.Class;
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
@RequestMapping("class")
public class ClassController {
    @Autowired
    private ClassFeignClientService classService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "get/{cid}", method = RequestMethod.GET)
    public ResponseResultJson<Class> get(@PathVariable("cid") Integer cid) {
        try {
            Class cclass = classService.getClassByCid(cid);
            return ResponseResultJson.success("获取课堂信息成功", cclass);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("获取课堂信息失败", null);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "listStu/{cid}", method = RequestMethod.GET)
    public ResponseResultJson<List<Map<String, Object>>> listStu(@PathVariable("cid") Integer cid) {
        try {
            List<Map<String, Object>> resultMap = classService.listSimpleStudents(cid);
            return ResponseResultJson.success("获取课堂人员组成信息成功", resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("获取课堂人员组成信息失败", null);
        }
    }

}
