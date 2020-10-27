package jp.kobe_u.root.shelter_navi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobe_u.root.shelter_navi.entity.User;
import jp.kobe_u.root.shelter_navi.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Scope( "request" )
@RequestMapping( "/users" )
@Slf4j
public class UserController {
    // 別のServiceを作ったほうが良い？
    @Autowired
    private UserService userService;

    // id, pass以外に必要な情報アリ
    // RequestParamじゃなくてRequestBodyにする
    // serviceに投げるのはUserEntityに変換してからの方が良い？
    // /users/** はADMIN以外認証が必要なのにどう/createをたたくか
    // 新規登録用に/users/** 以外のアクセスポイントを用意する？　SignUpControllerとか
    @PostMapping( "/create" )
    public void createUser( @RequestBody @Validated User user ) {
        log.info( "Creating account " + user.getEmail() );

        // 正規表現とか使ってidに問題がないかチェックする？
        // Sevice層に実処理を投げる（同idのアカウントが存在しないかチェック）
        userService.createUser( user );
    }

    @GetMapping( "/{uid}/delete" )
    public void delete( @PathVariable String id ) {
        userService.deleteUser( id );
    }

    @GetMapping( "/info" )
    public void getUserInfo( @AuthenticationPrincipal User user ) {
        // AuthenticationPrincipalで引数をとると直前に認証が通ったユーザ情報を自動的にとってくれるっぽい？

        // user.getNameとか使ってUserが存在しているかを確認
        // Dtoとかに変換してResponseとして返す
    }
}
