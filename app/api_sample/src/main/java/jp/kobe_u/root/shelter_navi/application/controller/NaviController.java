package jp.kobe_u.root.shelter_navi.application.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobe_u.root.shelter_navi.application.form.UserForm;

@Controller
public class NaviController {
    /*
    @RequestMapping( "/signup" )
    public String showSignupForm( ) {
        return "signup";
    }
*/

    @RequestMapping( "/create" )
    public String showSignupForm( Model model ) {
        model.addAttribute("userForm", new UserForm());
        return "signup";
    }

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

    @RequestMapping( "/" )
	public String redirectToMainPage() {
		return "redirect:/login";
    }

    @GetMapping( "/main" )
    public String showMainPage() {
        return "main";
    }

    @GetMapping( "/search" )
    public String showSearchPage() {
        return "search";
    }

    @GetMapping( "/search/{shelterId}" )
    public String showSearchOnePage( @PathVariable Long id ) {
        // idをもとにServiceのgetShelterを呼ぶ？
        // 検索結果をModelにセットしてページを返す

        return "search";
    }

    @GetMapping( "/checkin" )
    public String showCheckinPage() {
        return "checkin";
    }

    @GetMapping( "/myshelter" )
    public String showMyShelterPage() {
        return "myshelter";
    }
}
