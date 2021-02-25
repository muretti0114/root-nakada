package jp.kobe_u.root.shelter_navi.application.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobe_u.root.shelter_navi.domain.service.CheckinService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping( "/users/checkin" ) // (/api/users)
@Slf4j
public class CheckinController {
    @Autowired
    private CheckinService checkinService;

    // 避難所にいるかどうかをチェックする処理をどこにいれるか
    @PostMapping( "" )
    public String checkin( @RequestParam( name = "shelter_id" ) Long sid, Principal principal, Model model ) {

        System.out.println( principal );

        String uid = principal.getName();   // principalからUserIDどうやって取るのか

        checkinService.checkin( uid, sid );

        return "main";
    }
}
