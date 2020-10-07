package jp.kobe_u.root.shelter_navi.controller;

import java.util.List;

//import java.util.regex.Pattern;
//import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import jp.kobe_u.root.shelter_navi.controller.response.ShelterNaviResponse;
import jp.kobe_u.root.shelter_navi.entity.Shelter;
import jp.kobe_u.root.shelter_navi.service.ShelterNaviService;

@RestController
@Scope( "request" )
@RequestMapping( "/shelters" )
@Slf4j // logを扱うのに必要
public class ShelterNaviUserController {
    
    @Autowired
    private ShelterNaviService shelterNaviService;
    
    @GetMapping( "/{shelter_id}" )
    public ShelterNaviResponse getShelter( @PathVariable( "shelter_id" ) Long shelter_id ) {
        log.info( "Getting shelter " + shelter_id + "..." );

        Shelter shelter = shelterNaviService.getShelter( shelter_id );

        return ShelterNaviResponse.createSuccessResponse( shelter );
    }

    @GetMapping( "/search/distance" )
    public ShelterNaviResponse searchSheltersByDistance( @RequestParam Double userLng, @RequestParam Double userLat, @RequestParam Double distance ) {
        List<Shelter> shelter_list = shelterNaviService.searchSheltersByDistance( userLng, userLat, distance );

        return ShelterNaviResponse.createSuccessResponse( shelter_list );
    }

    @GetMapping( "/search/keyword" )
    public ShelterNaviResponse searchSheltersByKeyword( @RequestParam String keyword ) {
        List<Shelter> shelter_list = shelterNaviService.searchSheltersByKeyword( keyword );

        return ShelterNaviResponse.createSuccessResponse( shelter_list );
    }

    @GetMapping( "" )
    public ShelterNaviResponse getAllShelters() {
        log.info( "Getting all shelters..." );

        return ShelterNaviResponse.createSuccessResponse( shelterNaviService.getAllShelters() );
    }
}