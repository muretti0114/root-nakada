package jp.kobe_u.root.shelter_navi.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.kobe_u.root.shelter_navi.dto.UserSession;
import jp.kobe_u.root.shelter_navi.entity.User;
import jp.kobe_u.root.shelter_navi.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository users;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Controllerで作ったUserEntityを引数で受けるべき？
    public User createUser( User user ) {
        if ( users.existsById( user.getEmail() ) ) {
            // User用のExceptionを投げる
        }

        String password = passwordEncoder.encode( user.getPassword() );

        // encodeしたパスワードとid，その他の情報を使ってUserを作成
        user.setPassword( password );

        return users.save( user );
    }

    public User getUser( String id ) {
        User user = users.findById( id ).orElseThrow( /*() -> new NOT_FOUND_Exception*/ );

        return user;
    }

    public void deleteUser( String id ) {
        users.deleteById( id );
    }

    /*
    public UserSession getUserInfo() {
        
    }
    */

    @Override
    public UserDetails loadUserByUsername( String id ) {
        User user = users.findById( id ).orElseThrow( /** () -> new NOT_FOUND_EXCEPTION */ );

        /**
         * 避難所の管理者と利用者で権限を分ける？
         * 権限を付与するためにUserDetailsを継承したUserSessionクラスを実装する必要あり
         * UserSessionクラスは通常のUser情報に権限を加えたもの？
         */

         // UserSessionに渡すための権限リストの作成
         List<GrantedAuthority> authorities = new ArrayList<>(); // 権限リスト
         User.Role role = user.getRole(); // ユーザの権限

         switch ( role ) {
            case CITIZEN:
                authorities.add( new SimpleGrantedAuthority( "ROLE_CITIZEN" ) );
                
                break;

            case GOVERNMENT:
                authorities.add( new SimpleGrantedAuthority( "ROLE_GOVERNMENT" ) );
                
                break;

            case ADMIN:
                authorities.add( new SimpleGrantedAuthority( "ROLE_CITIZEN" ) );
                authorities.add( new SimpleGrantedAuthority( "ROLE_GOVERNMENT" ) );
                authorities.add( new SimpleGrantedAuthority( "ROLE_ADMIN" ) );

                break;
            /*
            default:
                throw new INVALID_USER_ROLE的な
            */
         }

         // UserDtailsを継承したUserSessionを生成して返す
         UserSession userSession = new UserSession( user, authorities );

         return userSession;
    }

    @Transactional
    public void registerAdmin(String uid, String password) {
        User user = new User( uid, passwordEncoder.encode(password), "システム管理者", null,
                1, User.Role.ADMIN );
        users.save(user);
    }
}
