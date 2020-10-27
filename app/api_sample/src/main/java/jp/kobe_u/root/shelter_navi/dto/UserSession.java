package jp.kobe_u.root.shelter_navi.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.kobe_u.root.shelter_navi.entity.User;

public class UserSession implements UserDetails {
    private static final long serialVersionUID = 5481546178797722247L;

    // 利用者，または管理者情報？
    private User user;

    // userの権限，複数持たせる可能性あり
    private Collection< ? extends GrantedAuthority > authorities;

    public UserSession( User user, Collection< ? extends GrantedAuthority > authorities ) {
        this.user = user;
        this.authorities = authorities;
    }

    // 権限の取得
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // パスワードの取得
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // ユーザ名（ここではユーザID）を取得する
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * アカウントが期限切れになっていないか
     * 期限が切れていなければtrueを返す
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * アカウントがロックされていないか
     * ロックされていなければtrueを返す
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 認証？が期限切れになっていないか
     * 期限切れでなければtrue
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * アカウントが有効か
     * 有効なアカウントであればtrueを返す
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


    public String getName() {
        return user.getName();
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    public Integer getNumOfHouseholds() {
        return user.getNumOfHouseholds();
    }

    public String getRole() {
        return user.getRole().name();
    }
}
