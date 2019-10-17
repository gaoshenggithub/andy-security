package cn.andy;

import cn.andy.code.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class TestController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @GetMapping("/getMap")
    public Map getMap(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        return map;
    }

    @PostMapping("register")
    public void register(User user, HttpServletRequest request){
        String username = user.getUsername();
        providerSignInUtils.doPostSignUp(username,new ServletWebRequest(request));
        //注册用户
    }
}
