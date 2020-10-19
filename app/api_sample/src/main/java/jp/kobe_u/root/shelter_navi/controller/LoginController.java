package jp.kobe_u.root.shelter_navi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    // thymeleaf使わないならいらない？
    @RequestMapping( "/login" )
    public void login() {

    }
}
