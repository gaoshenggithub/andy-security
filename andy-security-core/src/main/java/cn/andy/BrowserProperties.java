package cn.andy;

import lombok.Data;

@Data
public class BrowserProperties {

    private String signPage = "/signUp.html";
    private String loginPage = "/index.html";
    private LoginType loginType = LoginType.JSON;
    private int rememberMeSeconds = 3600;
}
