package cn.fzn.classsign.cinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class CinterfaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinterfaceApplication.class, args);
    }
}
