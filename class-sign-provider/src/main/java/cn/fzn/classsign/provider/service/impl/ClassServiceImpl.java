package cn.fzn.classsign.provider.service.impl;

import cn.fzn.classsign.common.entity.Class;
import cn.fzn.classsign.common.entity.User;
import cn.fzn.classsign.provider.dao.ClassDao;
import cn.fzn.classsign.provider.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassDao classDao;

    @Override
    public Class getClassByCid(Integer cid) {
        return classDao.getClassByCid(cid);
    }

    @Override
    public List<Map<String, Object>> listSimpleStudents(Integer cid) {
        return classDao.listSimpleStudents(cid);
    }

    @Override
    public List<User> listStudents(Integer cid) {
        return classDao.listStudents(cid);
    }

    @Override
    public Integer createClass(Class cclass) {
        classDao.createClass(cclass);
        return cclass.getCid();
    }

    @Override
    public Boolean updateClass(Class cclass) {
        return classDao.updateClass(cclass);
    }

    @Override
    public List<Map<String, Object>> listBaseClassInfo(Integer uid) {
        return classDao.listBaseClassInfo(uid);
    }

    @Override
    public Integer getTotalByCid(Integer cid) {
        return classDao.getTotalByCid(cid);
    }

    @Override
    public Integer getCidByUidAndJoinCode(String joinCode) {
        return classDao.getCidByUidAndJoinCode(joinCode);
    }

    @Override
    public Boolean joinClass(Integer stuUid, Integer cid) {
        return classDao.joinClass(stuUid, cid);
    }

    @Override
    public Boolean exitClass(Integer uid, Integer cid) {
        return classDao.exitClass(uid, cid);
    }

    @Override
    public List<Map<String, Object>> getStudentClassList(Integer uid) {
        return classDao.getStudentClassList(uid);
    }

    @Override
    public Integer isStudentExistInClass(Integer cid, Integer uid) {
        return classDao.isStudentExistInClass(cid, uid);
    }

    @Override
    public boolean isClassJoinCodeExist(String classJoinCode) {
        return classDao.isClassJoinCodeExist(classJoinCode);
    }
}
