package cn.fzn.classsign.uaa.dao;

import cn.fzn.classsign.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RBACDao {

    String getPhoneByUid(Integer uid);
    User getUserByUid(String uid);
}
