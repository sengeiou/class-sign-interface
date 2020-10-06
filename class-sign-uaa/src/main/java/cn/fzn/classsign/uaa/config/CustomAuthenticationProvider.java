package cn.fzn.classsign.uaa.config;

import cn.fzn.classsign.uaa.service.SpringDataUserDetailsService;
import cn.fzn.classsign.uaa.service.UserService;
import cn.fzn.classsign.uaa.util.UaaRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Autowired
    private UaaRedisUtils uaaRedisUtils;
    @Autowired
    private SpringDataUserDetailsService userDetailsService;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Integer uid = Integer.parseInt(authentication.getName());
        String phone = userService.getPhoneByUid(uid);
        if (authentication.getCredentials() == null) {
            log.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        String password = (String) authentication.getCredentials();
        UserDetails userDetails;
        //检查用户名有效性
        try {
            userDetails = userDetailsService.loadUserByUsername(uid.toString());
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("not found this username");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //优先匹配密码
        if (passwordEncoder.matches(password, userDetails.getPassword().substring(8))) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        } else if (uaaRedisUtils.isCodeLogin(phone, password)) {
            //这里将password尝试作为手机验证码，然后和已发送的验证码检验，需要注意验证码有效期，是否已验证码等判断
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        } else {
            log.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
