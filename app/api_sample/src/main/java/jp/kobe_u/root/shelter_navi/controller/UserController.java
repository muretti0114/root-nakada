package jp.kobe_u.root.shelter_navi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Scope( "request" )
@RequestMapping( "/accounts" )
@Slf4j
public class UserController {
    // 別のServiceを作ったほうが良い？
    @Autowired
    // private ;

    @PostMapping( "/create" )
    public void createAccount( @RequestParam() String id ) {
        log.info( "Creating account " + id );

        // 正規表現とか使ってidに問題がないかチェックする？
        // Sevice層に実処理を投げる（同idのアカウントが存在しないかチェック）
    }
}
