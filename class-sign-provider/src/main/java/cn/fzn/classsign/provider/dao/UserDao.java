package cn.fzn.classsign.provider.dao;

import cn.fzn.classsign.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    Integer getUidByPhone(String phone);

    Boolean addUser(User user);

    User getUserByUid(Integer uid);

    Boolean updatePasswordByUid(Integer uid, String encodePassword);

    Boolean updateUser(User user);

    List<User> listStudentsByCid(List<Integer> cid);

    User getUserByPhone(String phone);
}
