package cn.fzn.classsign.teacher.config.resourceconfig;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RequestTokenConfig {
    private final String CLIENT_ID = "classSignAndroid";
    private final String CLIENT_SECRET = "classSignAndroid";
    private final String LOGIN_GRANT_TYPE = "password";
    private final String REFRESH_GRANT_TYPE = "refresh_token";
    private String username;
    private String password;
}
