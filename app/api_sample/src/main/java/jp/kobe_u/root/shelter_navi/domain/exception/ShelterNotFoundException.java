package jp.kobe_u.root.shelter_navi.domain.exception;

public class ShelterNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public static final int ACCOUNT_NOT_FOUND = 1;

    public final int code;

    public ShelterNotFoundException( int code, String str ) {
        super( str );
        this.code = code;
    }
}