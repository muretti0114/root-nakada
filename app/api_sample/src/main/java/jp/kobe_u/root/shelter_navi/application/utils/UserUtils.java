package jp.kobe_u.root.shelter_navi.application.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jp.kobe_u.root.shelter_navi.application.dto.UserSession;

public class UserUtils {
    public static UserSession getUserSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserSession userSession = (UserSession) auth.getPrincipal();
        return userSession;
    }
}
