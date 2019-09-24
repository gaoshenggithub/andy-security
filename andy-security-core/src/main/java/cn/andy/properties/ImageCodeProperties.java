package cn.andy.properties;

import lombok.Data;

/**
 * 验证码参数可配置
 */
@Data
public class ImageCodeProperties {
    private int width = 67;
    private int height = 23;
    private int length = 4;
    private int expireIn = 60;
    private String url;
}
