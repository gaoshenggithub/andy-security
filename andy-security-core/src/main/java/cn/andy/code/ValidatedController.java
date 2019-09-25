package cn.andy.code;

import cn.andy.SecurityProperties;
import cn.andy.code.sms.SmsCodeSender;
import cn.andy.properties.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

@RestController
public class ValidatedController {
    // public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    //private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 系统配置
     */
    //@Autowired
    //private SecurityProperties securityProperties;

    // @Autowired
    //private ValidateCodeGenerator imageCodeGenerator;

    //@Autowired
    //private SmsCodeSender smsCodeSender;

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessor;

    /**
     * 创建也验证码
     * @param response
     * @param request
     * @param type
     * @throws Exception
     */
    @GetMapping("/code/{type}")
    public void createCode(HttpServletResponse response, HttpServletRequest request, @PathVariable String type) throws Exception {
        validateCodeProcessor.get(type + "CodeProcessor").create(new ServletWebRequest(request, response));
    }

  /*  @GetMapping("/code/image")
    public void createCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }*/


/*    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletRequestBindingException {
        ValidateCode validateCode = imageCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, validateCode);
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }*/
}
