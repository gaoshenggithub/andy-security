package cn.andy.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
    ImageCode generate(ServletWebRequest request);
}
