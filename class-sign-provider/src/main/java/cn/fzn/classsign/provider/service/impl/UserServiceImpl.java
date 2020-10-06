package cn.fzn.classsign.provider.service.impl;

import cn.fzn.classsign.common.entity.User;
import cn.fzn.classsign.provider.dao.UserDao;
import cn.fzn.classsign.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer getUidByPhone(String phone) {
        return userDao.getUidByPhone(phone);
    }

    @Override
    public Boolean addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public User getUserByUid(Integer uid) {
        return userDao.getUserByUid(uid);
    }

    @Override
    public Boolean updatePasswordByUid(Integer uid, String encodePassword) {
        return userDao.updatePasswordByUid(uid, encodePassword);
    }

    @Override
    public Boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public List<User> listStudentsByCid(List<Integer> cid) {
        return userDao.listStudentsByCid(cid);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }
}
