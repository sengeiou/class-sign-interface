package cn.fzn.classsign.provider.controller;

import cn.fzn.classsign.common.entity.Class;
import cn.fzn.classsign.common.entity.User;
import cn.fzn.classsign.common.service.ClassFeignService;
import cn.fzn.classsign.provider.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
public class ClassController implements ClassFeignService {
    @Autowired
    private ClassService classService;
    @Override
    public Class getClassByCid(Integer cid) {
        return classService.getClassByCid(cid);
    }

    @Override
    public List<Map<String, Object>> listSimpleStudents(Integer cid) {
        return classService.listSimpleStudents(cid);
    }

    @Override
    public List<User> listStudents(Integer cid) {
        return classService.listStudents(cid);
    }

    @Override
    public Integer createClass(Class cclass) {
        return classService.createClass(cclass);
    }

    @Override
    public Boolean updateClass(Class cclass) {
        return classService.updateClass(cclass);
    }

    @Override
    public List<Map<String, Object>> listBaseClassInfo(Integer uid) {
        return classService.listBaseClassInfo(uid);
    }

    @Override
    public Integer getTotalByCid(Integer cid) {
        return classService.getTotalByCid(cid);
    }

    @Override
    public Integer getCidByUidAndJoinCode(String joinCode) {
        return classService.getCidByUidAndJoinCode(joinCode);
    }

    @Override
    public Boolean joinClass(Integer stuUid, Integer cid) {
        return classService.joinClass(stuUid,cid);
    }

    @Override
    public Boolean exitClass(Integer uid, Integer cid) {
        return classService.exitClass(uid,cid);
    }

    @Override
    public List<Map<String, Object>> getStudentClassList(Integer uid) {
        return classService.getStudentClassList(uid);
    }

    @Override
    public Integer isStudentExistInClass(Integer cid, Integer uid) {
        return classService.isStudentExistInClass(cid,uid);
    }

    @Override
    public boolean isClassJoinCodeExist(String classJoinCode) {
        return classService.isClassJoinCodeExist(classJoinCode);
    }
}
