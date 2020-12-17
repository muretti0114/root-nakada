package jp.kobe_u.root.shelter_navi.application.controller.response;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ShelterNaviResponse {
    public static final String CODE_OK = "OK";
    public static final String ERROR_PREFIX = "E";
    public static final int DEFAULT_ERROR_CODE = 99;

    private String code;
    private String message;
    private Object result;

    private ShelterNaviResponse() {

    }

    public static ShelterNaviResponse createSuccessResponse( Object data, String message ) {
        ShelterNaviResponse res = new ShelterNaviResponse();

        res.setCode( CODE_OK );
        res.setMessage( message );
        res.setResult( data );

        log.info( message );

        return res;
    }

    public static ShelterNaviResponse createSuccessResponse( Object data ) {
        return createSuccessResponse( data, null );
    }

    public static ShelterNaviResponse createErrorResponse( int code, Exception exception ) {
        ShelterNaviResponse res = new ShelterNaviResponse();

        res.setCode( ERROR_PREFIX + String.format( "%02d", code ) );
        res.setMessage( exception.getMessage() );
        res.setResult( null );

        return res;
    }
}