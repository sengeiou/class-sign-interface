package cn.fzn.classsign.teacher.controller;

import cn.fzn.classsign.common.entity.Class;
import cn.fzn.classsign.common.response.ResponseResultJson;
import cn.fzn.classsign.teacher.service.ClassFeignClientService;
import cn.fzn.classsign.teacher.service.UserFeignClientService;
import cn.fzn.classsign.teacher.util.TeacherRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("class")
public class ClassController {
    @Autowired
    private ClassFeignClientService classService;
    @Autowired
    private UserFeignClientService userService;
    @Autowired
    private TeacherRedisUtils teacherRedisUtils;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseResultJson<Class> create(@RequestParam String cNum, @RequestParam String name) {
        Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        try {
            String tName = userService.getUserByUid(uid).getName();
            String classJoinCode;
            do {
                classJoinCode = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
            } while (classService.isClassJoinCodeExist(classJoinCode));
            Class cclass = new Class()
                    .setCNum(cNum)
                    .setName(name)
                    .setTName(tName)
                    .setUid(uid)
                    .setJoinCode(classJoinCode)
                    .setTotal(0);
            Integer cid = classService.createClass(cclass);
            cclass.setCid(cid);
            teacherRedisUtils.createClassJoinCodeCache(classJoinCode, cid);
            return ResponseResultJson.success("创建课堂成功", cclass);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", null);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "update", method = RequestMethod.PATCH)
    public ResponseResultJson<Boolean> update(@RequestBody Map<String, Object> params) {
        Integer cid = Integer.parseInt(params.get("cid").toString());
        String cNum = params.get("cNum").toString();
        String name = params.get("name").toString();
        String tName = params.get("tName").toString();

        Class cclass = new Class()
                .setCid(cid)
                .setCNum(cNum)
                .setName(name)
                .setTName(tName);
        try {
            classService.updateClass(cclass);
            return ResponseResultJson.success("修改课堂信息成功", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", Boolean.FALSE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseResultJson<List<Map<String, Object>>> list() {
        Integer uid = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        try {
            List<Map<String, Object>> list = classService.listBaseClassInfo(uid);
            return ResponseResultJson.success("获取课堂列表成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultJson.failed("未知错误", null);
        }
    }

}
