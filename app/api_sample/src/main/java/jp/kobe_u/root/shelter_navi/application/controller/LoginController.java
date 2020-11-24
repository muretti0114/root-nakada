package jp.kobe_u.root.shelter_navi.application.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//import jp.kobe_u.root.shelter_navi.application.controller.response.ShelterNaviResponse;
//import jp.kobe_u.root.shelter_navi.domain.exception.ShelterNaviException;
//import lombok.extern.slf4j.Slf4j;

@Controller
public class LoginController {
    // thymeleaf使わないならいらない？
    @RequestMapping( "/login" )
    public String showLoginForm( @RequestParam Map<String, String> params, Model model ) {
        //System.out.println( params );
        //System.out.println( "loginController" );

        if ( params.containsKey( "logout" ) ) {
            //return "login.html";
            model.addAttribute( "message","logout" );
		} else if ( params.containsKey( "error" ) ) {
            //log.warn( "error" );
            //return "error.html";
            //System.out.println( "error" );
            model.addAttribute( "message","error" );
        } /*else {
            throw new ShelterNaviException( ShelterNaviException.ERROR, "ログインに失敗" );
        }*/
        
        return "login";
    }

    @RequestMapping("/")
	public String redirectToMainPage() {
		return "redirect:/login";
    }
    
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
            return "Hello World";
    }
}