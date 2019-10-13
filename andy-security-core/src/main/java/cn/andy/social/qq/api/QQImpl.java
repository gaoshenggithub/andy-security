package cn.andy.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";


    private final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;
    private String openId;

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String result = getRestTemplate().getForObject(String.format(URL_GET_OPENID, accessToken), String.class);
        log.info("获取参数为===>{}", result);
        this.openId = StringUtils.substringBetween(result, "\"openId\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String result = getRestTemplate().getForObject(String.format(URL_GET_USERINFO, appId, openId), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            QQUserInfo qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
