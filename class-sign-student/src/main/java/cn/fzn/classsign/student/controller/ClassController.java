package cn.fzn.classsign.student.controller;

import cn.fzn.classsign.common.response.ResponseResultJson;
import cn.fzn.classsign.student.service.ClassFeignClientService;
import cn.fzn.classsign.student.util.StudentRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("class")
public class ClassController {
    @Autowired
    private ClassFeignClientService classService;
    @Autowired
    private StudentRedisUtils studentRedisUtils;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseResultJson<Boolean> add(@RequestParam String joinCode) {
        try {
            joinCode=joinCode.toUpperCase();
            Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            Integer cid = studentRedisUtils.getClassId(joinCode);
            if (cid == null) {
                cid = classService.getCidByUidAndJoinCode(joinCode);
            }
            if (classService.isStudentExistInClass(cid, uid) > 0) {
                return ResponseResultJson.failed("已加入该课堂", Boolean.TRUE);
            }
            Integer stuUid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            classService.joinClass(stuUid, cid);
            return ResponseResultJson.success("加入课堂成功", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", Boolean.FALSE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "exit/{cid}", method = RequestMethod.GET)
    public ResponseResultJson<Boolean> add(@PathVariable("cid") Integer cid) {
        try {
            Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            classService.exitClass(uid, cid);
            return ResponseResultJson.success("退出课堂成功", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", Boolean.FALSE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseResultJson<List<Map<String, Object>>> list() {
        try {
            Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            List<Map<String, Object>> classList = classService.getStudentClassList(uid);
            return ResponseResultJson.success("获取课堂列表成功", classList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", null);
        }
    }
}
