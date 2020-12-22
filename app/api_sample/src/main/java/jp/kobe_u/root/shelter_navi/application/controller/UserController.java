package jp.kobe_u.root.shelter_navi.application.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobe_u.root.shelter_navi.application.controller.response.ShelterNaviResponse;
import jp.kobe_u.root.shelter_navi.domain.entity.User;
import jp.kobe_u.root.shelter_navi.domain.exception.ShelterNaviException;
import jp.kobe_u.root.shelter_navi.application.form.UserForm;
import jp.kobe_u.root.shelter_navi.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Controller
@RequestMapping( "/users" ) // (/api/users)
@Slf4j
public class UserController {
    // 別のServiceを作ったほうが良い？
    @Autowired
    private UserService userService;

    /*
    @RequestMapping( "/create" )
    public String showSignupForm( Model model ) {
        model.addAttribute("userForm", new UserForm());
        return "signup";
    }
*/
    // id, pass以外に必要な情報アリ
    // RequestParamじゃなくてRequestBodyにする
    // serviceに投げるのはUserEntityに変換してからの方が良い？
    // /users/** はADMIN以外認証が必要なのにどう/createをたたくか
    // 新規登録用に/users/** 以外のアクセスポイントを用意する？　SignUpControllerとか
    @PostMapping( "" )
    public String createUser( @ModelAttribute( "userForm" ) @Validated UserForm userForm, BindingResult result, Model model ) {
        
        System.out.println( userForm.getEmail() );
        
        if ( result.hasErrors() ) {
            System.err.println( result );
            return "signup";
        }
        
        if ( ! userForm.getPassword().equals( userForm.getPassword2() ) ) return "signup";// throw new ShelterNaviException( ShelterNaviException.INVALID_USER_FORM, "入力されたパスワードが確認用パスワードと異なります．" );

        log.info( "Creating account " + userForm.getEmail() );

        // 正規表現とか使ってidに問題がないかチェックする？
        // Sevice層に実処理を投げる（同idのアカウントが存在しないかチェック）
        User user = userService.createUser( userForm.toEntity() );

        // 作成したuserのemailをLoginFormに入力した状態でloginを返してあげると良さそう？
        return "login";
        // return ShelterNaviResponse.createSuccessResponse( user, "Account: " + user.getEmail() + " created" );
    }

    /*
    @GetMapping( "/{uid}/delete" )
    public ShelterNaviResponse delete( @PathVariable String id ) {
        userService.deleteUser( id );

        return ShelterNaviResponse.createSuccessResponse( null, "Account: " + id + " deleted" );
    }

    @GetMapping( "/info" )
    public Principal getUserInfo( Principal principal ) {
        /* AuthenticationPrincipalで引数をとると直前に認証が通ったユーザ情報を自動的にとってくれるっぽい？

        /* user.getNameとか使ってUserが存在しているかを確認
        /* Dtoとかに変換してResponseとして返す
        return principal;
    }
    */
}
