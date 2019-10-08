package cn.andy.code;

import cn.andy.SecurityProperties;
import cn.andy.properties.ValidateCodeProcessor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private Set<String> urls = new HashSet<>();
    @Setter
    @Getter
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Setter
    @Getter
    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(), "");
        Arrays.asList(configUrls).forEach(e -> urls.add(e));
        urls.add("/auth/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        *//*if (StringUtils.equals("/auth/form", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase("POST", httpServletRequest.getMethod())) {*//*
        boolean action = false;
        for (String url : urls) {
            if (antPathMatcher.match(url, httpServletRequest.getRequestURI())) {
                action = true;
            }
        }
        if (action) {
            try {
                vaildate(new ServletWebRequest(httpServletRequest));
            } catch (VaildateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void vaildate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        ValidateCode imageCode = (ValidateCode) sessionStrategy.getAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
        String code = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "smsCode");
        if (StringUtils.isBlank(code)) {
            throw new VaildateCodeException("验证码不能为空");
        }
        if (imageCode == null) {
            throw new VaildateCodeException("验证码不存在");
        }
        if (imageCode.isExpried()) {
            sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
            throw new VaildateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(imageCode.getCode(), code)) {
            throw new VaildateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
    }

}*/
