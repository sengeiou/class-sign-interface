package cn.fzn.classsign.uaa.service;

import cn.fzn.classsign.uaa.dao.RBACDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private RBACDao rbacDao;

    public String getPhoneByUid(Integer uid) {
        return rbacDao.getPhoneByUid(uid);
    }
}
