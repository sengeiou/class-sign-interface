package cn.fzn.classsign.sms.service;

import cn.fzn.classsign.common.service.UserFeignService;
import cn.fzn.classsign.sms.service.fallBackFactory.UserServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "SERVICE-PROVIDER",fallbackFactory = UserServiceFallBackFactory.class)
public interface UserFeignClientService extends UserFeignService {
}
