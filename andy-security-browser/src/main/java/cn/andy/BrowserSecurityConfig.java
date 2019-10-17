package cn.andy;

import cn.andy.auth.AbstractChannelSecurityConfig;
import cn.andy.auth.AndyAuthenticationFailureHandler;
import cn.andy.auth.AndyAuthenticationSuccessHandler;
//import cn.andy.code.SmsCodeFilter;
import cn.andy.code.VaildateCodeFilter;
import cn.andy.code.ValidateCodeSecurityConfig;
import cn.andy.mobile.SmsCodeAuthenticationSecurityConfig;
import cn.andy.properties.SecurityConstants;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.validation.annotation.Validated;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
    //extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AndyAuthenticationSuccessHandler andyAuthenticationSuccessHandler;

    @Autowired
    private AndyAuthenticationFailureHandler andyAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

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
        applyPasswordAuthenticationConfig(http);

        /*http.apply(validateCodeSecurityConfig).and().rememberMe().
                tokenRepository(persistentTokenRepository()).
                tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds()).
                userDetailsService(userDetailsService).and().
                authorizeRequests().antMatchers("/get/getMap", "/me", "/code/*", "/auth/require", securityProperties.getBrowser().getLoginPage()).permitAll().
                anyRequest().
                authenticated().
                and().
                csrf().
                disable().
                apply(smsCodeAuthenticationSecurityConfig);
        http.//httpBasic().
                formLogin().
                and().
                authorizeRequests().
                anyRequest().authenticated();*/
        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(springSocialConfigurer).and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*", "/user/register",
                        securityProperties.getBrowser().getLoginPage(), securityProperties.getBrowser().getSignPage()).permitAll().
                anyRequest().
                authenticated().
                and().
                csrf().
                disable().apply(smsCodeAuthenticationSecurityConfig);
    }

 /*   @Override
    protected void configure(HttpSecurity http) throws Exception {
        VaildateCodeFilter vaildateCodeFilter = new VaildateCodeFilter();
        vaildateCodeFilter.setAuthenticationFailureHandler(andyAuthenticationFailureHandler);
        vaildateCodeFilter.setSecurityProperties(securityProperties);
        vaildateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(andyAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();


        http.
                addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class).
                addFilterBefore(vaildateCodeFilter, UsernamePasswordAuthenticationFilter.class).
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
                authorizeRequests().antMatchers("/get/getMap", "/me", "/code/*", "/auth/require", securityProperties.getBrowser().getLoginPage()).permitAll().
                anyRequest().
                authenticated().
                and().
                csrf().
                disable().
                apply(smsCodeAuthenticationSecurityConfig);
        http.//httpBasic().
                formLogin().
                and().
                authorizeRequests().
                anyRequest().authenticated();
    }*/
}
