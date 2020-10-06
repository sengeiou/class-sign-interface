package cn.fzn.classsign.sms.service.fallBackFactory;

import cn.fzn.classsign.common.entity.User;
import cn.fzn.classsign.sms.service.UserFeignClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceFallBackFactory implements FallbackFactory<UserFeignClientService> {
    @Override
    public UserFeignClientService create(Throwable throwable) {
        return new UserFeignClientService() {
            @Override
            public Integer getUidByPhone(String phone) {
                return null;
            }

            @Override
            public Boolean addUser(User user) {
                return null;
            }

            @Override
            public User getUserByUid(Integer uid) {
                return null;
            }

            @Override
            public Boolean updatePasswordByUid(Integer uid, String encodePassword) {
                return null;
            }

            @Override
            public Boolean updateUser(User user) {
                return null;
            }

            @Override
            public List<User> listStudentsByCid(List<Integer> cid) {
                return null;
            }

            @Override
            public User getUserByPhone(String phone) {
                return null;
            }
        };
    }
}
