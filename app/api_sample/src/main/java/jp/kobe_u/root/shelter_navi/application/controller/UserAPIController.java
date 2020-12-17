package jp.kobe_u.root.shelter_navi.application.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobe_u.root.shelter_navi.application.controller.response.ShelterNaviResponse;
import jp.kobe_u.root.shelter_navi.domain.entity.User;
import jp.kobe_u.root.shelter_navi.domain.exception.ShelterNaviException;
import jp.kobe_u.root.shelter_navi.application.form.UserForm;
import jp.kobe_u.root.shelter_navi.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Scope( "request" )
@RequestMapping( "api/users" ) // (/api/users)
@Slf4j
public class UserAPIController {
    // 別のServiceを作ったほうが良い？
    @Autowired
    private UserService userService;

    // id, pass以外に必要な情報アリ
    // RequestParamじゃなくてRequestBodyにする
    // serviceに投げるのはUserEntityに変換してからの方が良い？
    // /users/** はADMIN以外認証が必要なのにどう/createをたたくか
    // 新規登録用に/users/** 以外のアクセスポイントを用意する？　SignUpControllerとか
    @PostMapping( "/create" )
    public ShelterNaviResponse createUser( @RequestBody @Validated UserForm userForm ) {
        
        System.out.println( userForm.getEmail() );
        
        if ( ! userForm.getPassword().equals( userForm.getPassword2() ) ) throw new ShelterNaviException( ShelterNaviException.INVALID_USER_FORM, "入力されたパスワードが確認用パスワードと異なります．" );

        log.info( "Creating account " + userForm.getEmail() );

        // 正規表現とか使ってidに問題がないかチェックする？
        // Sevice層に実処理を投げる（同idのアカウントが存在しないかチェック）
        User user = userService.createUser( userForm.toEntity() );

        return ShelterNaviResponse.createSuccessResponse( user, "Account: " + user.getEmail() + " created" );
    }

    @GetMapping( "/{uid}/delete" )
    public ShelterNaviResponse delete( @PathVariable String id ) {
        userService.deleteUser( id );

        return ShelterNaviResponse.createSuccessResponse( null, "Account: " + id + " deleted" );
    }

    @GetMapping( "/info" )
    public Principal getUserInfo( Principal principal/*@AuthenticationPrincipal User user*/ ) {
        // AuthenticationPrincipalで引数をとると直前に認証が通ったユーザ情報を自動的にとってくれるっぽい？

        // user.getNameとか使ってUserが存在しているかを確認
        // Dtoとかに変換してResponseとして返す
        return principal;
    }
}
