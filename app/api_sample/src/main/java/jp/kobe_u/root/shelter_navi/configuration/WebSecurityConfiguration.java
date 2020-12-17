package jp.kobe_u.root.shelter_navi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.kobe_u.root.shelter_navi.domain.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminConfiguration adminConfig;

    @Override
    public void configure( WebSecurity web ) throws Exception {
        web.ignoring().antMatchers( "/img/**", "/css/**", "/js/**" );
    }

    // login成功後に別ページに飛ばす
    // thymeleafを使っていないので遷移先のURLを返すとか？
    // 中田君の知識箱を参照してみる
    // ログイン認証が成功しないと遷移できないURLからのみ叩けるuserInfo取得APIを作る
    // これによってログイン成功時にログイン後の遷移先URLを渡すだけで，後はそのページからuseInfo取得APIをたたけばUser情報を取得できる
    // 遷移先のURLを返すのではなく200とかのレスポンスを返す
    // ページ遷移はレスポンスの中身を見てクライアント側でやってもらう？
    // クライアント側でのページ遷移後にページ内で必要なユーザ情報等をgetUserInfoから取得
    // login後はいわゆるホーム画面的なとこに遷移？
    // 自治体の災害情報，避難情報を表示し，各機能へのリンクボタンを用意？
    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        // 認可の設定

        http.authorizeRequests()
        .antMatchers( "/login" ).permitAll()                // ログインページは誰でも許可
        .antMatchers( "/users/**" ).permitAll()
        .antMatchers( "/signup" ).permitAll()
        .antMatchers( "/hello/**" ).permitAll()
        //.antMatchers( "/shelters/**" ).permitAll()
        //.antMatchers( "/users/create" ).permitAll()         // users/create も誰でもできるようにする？
        //.antMatchers( "/admin/users/**" ).hasRole( "ADMIN" )
        //.antMatchers( "/admin/users/**" ).hasRole( "CITIZEN" )
        //.antMatchers( "/users/**" ).hasRole( "ADMIN" )
        //.antMatchers( "/users/info" ).hasRole( "CITIZEN" )
        .antMatchers( "/admin/shelters/**" ).hasRole( "ADMIN" )
        .antMatchers( "/shelters/**" ).hasRole( "ADMIN" )
        //.antMatchers( "/admin/shelters/**" ).hasRole( "GOVERNMENT" )
        .anyRequest().authenticated();                     // それ以外は全て認証必要

        // ログインの設定
        // 遷移後のURI，ページで何をしたいのか要相談
        http.formLogin()
            .loginPage( "/login" )                          // ログインページ
            .loginProcessingUrl( "/authenticate" )          // フォームのPOST先URL．認証処理を実行する
            .usernameParameter( "email" )                   // ユーザ名に該当するリクエストパラメタ
            .passwordParameter( "password" )                // パスワードに該当するリクエストパラメタ
            .defaultSuccessUrl( "/main", true )         // 成功時のページ (trueは以前どこにアクセスしてもここに遷移する設定)
            .failureUrl( "/login?error" );                  // 失敗時のページ
            //.permitAll();

        // ログアウトの設定
        http.logout()
            .logoutUrl( "/logout" )                         // ログアウトのURL
            .logoutSuccessUrl( "/login?logout" )            // ログアウト完了したらこのページへ
            .deleteCookies( "JSESSIONID" )                  // クッキー削除
            .invalidateHttpSession( true )                  // セッション情報消去
            .permitAll();                                   // ログアウトはいつでもアクセスできる
    
        //http.csrf().disable();
    }

    @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        // (4) 認証方法の実装の設定を行う
        auth.userDetailsService( userService )   // 認証は予約アプリのユーザサービスを使う
            .passwordEncoder( passwordEncoder ); // パスワードはアプリ共通のものを使う
        // ついでにここで管理者ユーザを登録しておく
        userService.registerAdmin( adminConfig.getEmail(), adminConfig.getPassword() );
    }
}