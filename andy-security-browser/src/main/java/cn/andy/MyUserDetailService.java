package cn.andy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * UserDetailService 处理用户信息获取逻辑
 * UserDetail 处理用户校验逻辑
 * PassEncoder 密码加密
 * 自定义登录页面 http.formLogin().loginPage('html')
 * 自定义登录成功 AuthenticationSuccessHandler
 * 自定义登录失败 AuthenticationFailureHandler
 */
@Component
@Slf4j
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录名: ===>" + username);
        String encode = passwordEncoder.encode("123456");
        log.info(encode);
        return new User(username,encode,true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
