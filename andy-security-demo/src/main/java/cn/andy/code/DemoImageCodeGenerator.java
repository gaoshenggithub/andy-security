package cn.andy.code;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 出现相同的bean会报错,使用了    @ConditionalOnMissingBean(name = "imageCodeGenerator")
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("更加高级的代码生成器");
        return null;
    }
}
