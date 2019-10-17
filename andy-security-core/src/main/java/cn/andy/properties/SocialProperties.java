package cn.andy.properties;

import cn.andy.social.wechat.WeixinProperties;
import lombok.Data;

@Data
public class SocialProperties {

    private String filterProcessUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();
}
