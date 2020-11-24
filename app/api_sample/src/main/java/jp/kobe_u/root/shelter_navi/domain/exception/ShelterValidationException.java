package jp.kobe_u.root.shelter_navi.domain.exception;

public class ShelterValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public static final int SHELTER_ALREADY_EXISTS = 10;
    public static final int INVALID_CATEGORIES = 11;

    public final int code;

    public ShelterValidationException( int code, String str ) {
        super( str );
        this.code = code;
    }
}