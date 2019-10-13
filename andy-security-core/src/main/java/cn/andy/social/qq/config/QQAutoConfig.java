package cn.andy.social.qq.config;

import cn.andy.SecurityProperties;
import cn.andy.properties.QQProperties;
import cn.andy.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

@ConditionalOnProperty(prefix = "andy.security.social.qq", name = "app-id")
@Configuration
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    private @Autowired
    SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }
}
