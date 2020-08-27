package jp.kobe_u.root.shelter_navi.controller;

//import java.util.regex.Pattern;
//import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import jp.kobe_u.root.shelter_navi.controller.response.ShelterNaviResponse;
import jp.kobe_u.root.shelter_navi.entity.Shelter;
import jp.kobe_u.root.shelter_navi.form.ShelterForm;
import jp.kobe_u.root.shelter_navi.service.ShelterNaviService;

@RestController
@Scope( "request" )
@RequestMapping( "/admin/shelters" )
@Slf4j // logを扱うのに必要
public class ShelterNaviAdminController {
    
    @Autowired
    private ShelterNaviService shelterNaviService;

    @PostMapping( "/create" )
    //@RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ShelterNaviResponse createShelter( @RequestBody ShelterForm form ) {
        // 管理者に入力してもらった避難所情報(id以外？)をdtoで受け取る
        // log.info( "creating..." );
        // log.info( "id : " + form.getId() + " address : " + form.getAddress() +  " lng : " + form.getLng() + " name : " + form.getName() );
        
        // dtoが持つパラメータを基にService層にShelterインスタンスの生成を依頼する
        Shelter shelter = shelterNaviService.createShelter( form );

        return ShelterNaviResponse.createSuccessResponse( shelter );
    }
    
    @PostMapping( "/{shelter_id}/delete" )
    public ShelterNaviResponse deleteShelter( @PathVariable( "shelter_id" ) Long shelter_id ) {
        log.info( "Deleting shelter " + shelter_id + "..." );

        shelterNaviService.deleteShelter( shelter_id );

        return ShelterNaviResponse.createSuccessResponse( null, "Shelter " + shelter_id + "deleted" );
    }
    
    @PostMapping( "/clearAll" )
    public ShelterNaviResponse clearAllShelters() {
        log.info( "Clearing all shelters..." );

        shelterNaviService.clearAllShelters();

        return ShelterNaviResponse.createSuccessResponse( null, "All shelters cleared" );
    }

    @GetMapping( "" )
    public ShelterNaviResponse getAllShelters() {
        log.info( "Getting all shelters..." );

        return ShelterNaviResponse.createSuccessResponse( shelterNaviService.getAllShelters() );
    }
}