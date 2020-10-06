package cn.fzn.classsign.common.service;

import cn.fzn.classsign.common.entity.Class;
import cn.fzn.classsign.common.entity.User;
import org.springframework.beans.PropertyValues;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("class")
public interface ClassFeignService {
    @RequestMapping(value = "getClassByCid", method = RequestMethod.POST)
    public Class getClassByCid(@RequestParam Integer cid);

    @RequestMapping(value = "listSimpleStudents", method = RequestMethod.POST)
    public List<Map<String, Object>> listSimpleStudents(@RequestParam Integer cid);

    @RequestMapping(value = "listStudents", method = RequestMethod.POST)
    public List<User> listStudents(@RequestParam Integer cid);

    @RequestMapping(value = "createClass", method = RequestMethod.POST)
    public Integer createClass(@RequestBody Class cclass);

    @RequestMapping(value = "updateClass", method = RequestMethod.POST)
    public Boolean updateClass(@RequestBody Class cclass);

    /**
     * 教师端获取课堂列表
     *
     * @param uid 用户id
     * @return * 课号、课程名称、加课码、人数的集合
     */
    @RequestMapping(value = "listBaseClassInfo", method = RequestMethod.POST)
    public List<Map<String, Object>> listBaseClassInfo(@RequestParam Integer uid);

    @RequestMapping(value = "getTotalByCid", method = RequestMethod.POST)
    public Integer getTotalByCid(@RequestParam Integer cid);

    @RequestMapping(value = "getCidByUidAndJoinCode", method = RequestMethod.POST)
    public Integer getCidByUidAndJoinCode(@RequestParam String joinCode);

    @RequestMapping(value = "joinClass", method = RequestMethod.POST)
    public Boolean joinClass(@RequestParam Integer stuUid, @RequestParam Integer cid);

    @RequestMapping(value = "exitClass", method = RequestMethod.POST)
    public Boolean exitClass(@RequestParam Integer uid, @RequestParam Integer cid);

    /**
     * 学生端获取课堂列表
     *
     * @param uid 用户id
     * @return * 课号、课程名称、加课码、人数的集合
     */
    @RequestMapping(value = "getStudentClassList", method = RequestMethod.POST)
    public List<Map<String, Object>> getStudentClassList(@RequestParam Integer uid);

    @RequestMapping(value = "isStudentExistInClass", method = RequestMethod.POST)
    public Integer isStudentExistInClass(@RequestParam Integer cid, @RequestParam Integer uid);

    @RequestMapping(value = "isClassJoinCodeExist", method = RequestMethod.POST)
    public boolean isClassJoinCodeExist(@RequestParam String classJoinCode);
}
