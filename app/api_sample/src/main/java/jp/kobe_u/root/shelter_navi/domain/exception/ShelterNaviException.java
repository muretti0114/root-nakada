package jp.kobe_u.root.shelter_navi.domain.exception;

public class ShelterNaviException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public static final int USER_NOT_FOUND = 100;
    public static final int INVALID_USER_ROLE = 101;
    public static final int USER_ALREADY_EXISTS = 102;
    public static final int INVALID_USER_UPDATE = 103;
    public static final int INVALID_USER_FORM = 104;
    
    public static final int SHELTER_NOT_FOUND = 105;
    
    public static final int ERROR = 999;

    private int code;

    public ShelterNaviException( int code, String message ) {
        super( message );
        this.code = code;
    }

    public ShelterNaviException( int code, String message, Exception cause ) {
        super( message, cause );
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
