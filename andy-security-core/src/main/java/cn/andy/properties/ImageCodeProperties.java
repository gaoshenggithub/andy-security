package cn.andy.properties;

import lombok.Data;

/**
 * 验证码参数可配置
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties {
    public ImageCodeProperties() {
        setLength(4);
    }

    private int width = 67;
    private int height = 23;
}
