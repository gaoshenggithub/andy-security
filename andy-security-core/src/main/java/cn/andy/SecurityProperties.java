package cn.andy;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@ConfigurationProperties(prefix = "andy.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();
}