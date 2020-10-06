package cn.fzn.classsign.cinterface.service;

import cn.fzn.classsign.common.service.SignFeignService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "SERVICE-PROVIDER",contextId = "signService")
public interface SignFeignClientService extends SignFeignService {
}
