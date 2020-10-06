package cn.fzn.classsign.provider.controller;

import cn.fzn.classsign.common.entity.User;
import cn.fzn.classsign.common.service.UserFeignService;
import cn.fzn.classsign.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController implements UserFeignService {
    @Autowired
    private UserService userService;
    @Override
    public Integer getUidByPhone(String phone) {
        return userService.getUidByPhone(phone);
    }

    @Override
    public Boolean addUser(User user) {
        return userService.addUser(user);
    }

    @Override
    public User getUserByUid(Integer uid) {
        return userService.getUserByUid(uid);
    }

    @Override
    public Boolean updatePasswordByUid(Integer uid, String encodePassword) {
        return userService.updatePasswordByUid(uid,encodePassword);
    }

    @Override
    public Boolean updateUser(User user) {
        return userService.updateUser(user);
    }

    @Override
    public List<User> listStudentsByCid(List<Integer> cid) {
        return userService.listStudentsByCid(cid);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userService.getUserByPhone(phone);
    }
}
