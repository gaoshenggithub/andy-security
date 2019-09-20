package cn.andy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/get")
@RestController
public class TestController {

    @GetMapping("/getMap")
    public Map getMap(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        return map;
    }
}
