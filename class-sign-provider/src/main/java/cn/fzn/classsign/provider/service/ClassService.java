package cn.fzn.classsign.provider.service;

import cn.fzn.classsign.common.entity.Class;
import cn.fzn.classsign.common.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ClassService {
    Class getClassByCid(Integer cid);

    List<Map<String, Object>> listSimpleStudents(Integer cid);

    List<User> listStudents(Integer cid);

    Integer createClass(Class cclass);

    Boolean updateClass(Class cclass);

    List<Map<String, Object>> listBaseClassInfo(Integer uid);

    Integer getTotalByCid(Integer cid);

    Integer getCidByUidAndJoinCode(String joinCode);

    Boolean joinClass(Integer stuUid, Integer cid);

    Boolean exitClass(Integer uid, Integer cid);

    List<Map<String, Object>> getStudentClassList(Integer uid);

    Integer isStudentExistInClass(Integer cid, Integer uid);

    boolean isClassJoinCodeExist(String classJoinCode);
}
