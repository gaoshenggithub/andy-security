package cn.andy.properties;

import lombok.Data;

/**
 * 验证码参数可配置
 */
@Data
public class SmsCodeProperties {
    private int length = 6;
    private int expireIn = 60;
    private String url;
}
