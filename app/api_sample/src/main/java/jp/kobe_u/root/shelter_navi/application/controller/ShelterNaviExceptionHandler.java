package jp.kobe_u.root.shelter_navi.application.controller;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jp.kobe_u.root.shelter_navi.application.controller.response.ShelterNaviResponse;
import jp.kobe_u.root.shelter_navi.domain.exception.ShelterNaviException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ShelterNaviExceptionHandler {
    
    @ExceptionHandler( ShelterNaviException.class )
    public ShelterNaviResponse handleShelterNaviException( ShelterNaviException ex ) {
        log.warn( ex.getMessage() );

        return ShelterNaviResponse.createErrorResponse( ex.getCode(), ex );
    }

    /**
     * SpringBootのValidationで投げられた例外をShelterNaviExceptionに変換して処理する
     */
    @ExceptionHandler( BindException.class )
    public ShelterNaviResponse handleBindException( BindException ex ) {
        ShelterNaviException sn_exception = new ShelterNaviException( ShelterNaviException.INVALID_USER_FORM, ex.getMessage(), ex );

        return handleShelterNaviException( sn_exception );
    }

    @ExceptionHandler( Exception.class )
    public ShelterNaviResponse handleBindException( Exception ex ) {
        ShelterNaviException sn_exception = new ShelterNaviException( ShelterNaviException.ERROR, ex.getMessage(), ex );
        
        return handleShelterNaviException( sn_exception );
    }
}
