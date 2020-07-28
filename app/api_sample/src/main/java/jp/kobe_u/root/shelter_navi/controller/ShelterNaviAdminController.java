package jp.kobe_u.root.shelter_navi.controller;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import jp.kobe_u.root.shelter_navi.service.ShelterNaviService;

@RestController
@Scope( "request" )
@RequestMapping( "/admin" )
@Slf4j // logを扱うのに必要
public class ShelterNaviAdminController {
    
    @Autowired
    private ShelterNaviService shelterNaviService;

    @PostMapping( "/create" )
    public void createShelterInfo() {
        
    }

    
}