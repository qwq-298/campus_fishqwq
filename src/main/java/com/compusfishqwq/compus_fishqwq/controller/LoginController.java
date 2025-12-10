package com.compusfishqwq.compus_fishqwq.controller;

import com.compusfishqwq.compus_fishqwq.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.compusfishqwq.compus_fishqwq.entity.User;
//import com.compusfishqwq.compus_fishqwq.repository.UserRepository;


import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/login")
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password) {

        Map<String, Object> result = new HashMap<>();

        boolean ok = loginService.checkLogin(username,password);
        User user=loginService.getUser(username,password);
        if (ok) {
            result.put("success", true);
            result.put("msg", "登录成功");
            result.put("user", Map.of(
            "username", username,
            "name", user.getName(),
            "email", user.getEmail(),
            "id",user.getId()
            ));
        } else {
            result.put("success", false);
            result.put("msg", "用户名或密码错误");
        }
        //System.out.println(result);
        return result;
    }
}
/*package com.compusfishqwq.compus_fishqwq.controller;

import com.compusfishqwq.compus_fishqwq.entity.User;
import com.compusfishqwq.compus_fishqwq.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/login")
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password,
                                     HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        // 登录验证
        User user = loginService.getUser(username, password);

        if (user != null) {
            // ✔ 保存登录用户到 session
            session.setAttribute("loginUser", user);

            result.put("success", true);
            result.put("msg", "登录成功");
        } else {
            result.put("success", false);
            result.put("msg", "用户名或密码错误");
        }

        return result;
    }
}*/


