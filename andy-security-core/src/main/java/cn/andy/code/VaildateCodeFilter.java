package cn.andy.code;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VaildateCodeFilter extends OncePerRequestFilter {

    @Autowired
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Setter
    @Getter
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/auth/form", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase("POST", httpServletRequest.getMethod())) {
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
        ImageCode imageCode = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, ValidatedController.SESSION_KEY);
        String code = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
        if (StringUtils.isBlank(code)) {
            throw new VaildateCodeException("验证码不能为空");
        }
        if (imageCode == null) {
            throw new VaildateCodeException("验证码不存在");
        }
        if (imageCode.isExpried()) {
            sessionStrategy.removeAttribute(servletWebRequest, ValidatedController.SESSION_KEY);
            throw new VaildateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(imageCode.getCode(), code)) {
            throw new VaildateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(servletWebRequest, ValidatedController.SESSION_KEY);
    }

}
