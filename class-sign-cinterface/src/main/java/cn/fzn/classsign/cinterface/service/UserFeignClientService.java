package cn.fzn.classsign.cinterface.service;

import cn.fzn.classsign.common.service.UserFeignService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "SERVICE-PROVIDER",contextId = "userService")
public interface UserFeignClientService extends UserFeignService {
}
