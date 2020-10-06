package cn.fzn.classsign.provider.service;

import cn.fzn.classsign.common.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    Integer getUidByPhone(String phone);

    Boolean addUser(User user);

    User getUserByUid(Integer uid);

    Boolean updatePasswordByUid(Integer uid, String encodePassword);

    Boolean updateUser(User user);

    List<User> listStudentsByCid(List<Integer> cid);

    User getUserByPhone(String phone);
}
