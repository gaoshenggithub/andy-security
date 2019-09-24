package cn.andy;

import cn.andy.auth.AndyAuthenticationFailureHandler;
import cn.andy.auth.AndyAuthenticationSuccessHandler;
import cn.andy.code.VaildateCodeFilter;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.validation.annotation.Validated;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AndyAuthenticationSuccessHandler andyAuthenticationSuccessHandler;

    @Autowired
    private AndyAuthenticationFailureHandler andyAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean //可以自定义,实现PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
       // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        VaildateCodeFilter vaildateCodeFilter = new VaildateCodeFilter();
        vaildateCodeFilter.setAuthenticationFailureHandler(andyAuthenticationFailureHandler);
        vaildateCodeFilter.setSecurityProperties(securityProperties);
        vaildateCodeFilter.afterPropertiesSet();
        http.addFilterBefore(vaildateCodeFilter,
                UsernamePasswordAuthenticationFilter.class).
                formLogin().
                loginPage("/auth/require").
                loginProcessingUrl("/auth/form").
                successHandler(andyAuthenticationSuccessHandler).
                failureHandler(andyAuthenticationFailureHandler).
                and().
                rememberMe().
                tokenRepository(persistentTokenRepository()).
                tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds()).
                userDetailsService(userDetailsService).and().
                authorizeRequests().antMatchers("/get/getMap", "/me", "/code/image", "/auth/require", securityProperties.getBrowser().getLoginPage()).permitAll().
                anyRequest().authenticated().and().csrf().disable();
       /* http.//httpBasic().
                formLogin().
                and().
                authorizeRequests().
                anyRequest().authenticated();*/
    }
}
