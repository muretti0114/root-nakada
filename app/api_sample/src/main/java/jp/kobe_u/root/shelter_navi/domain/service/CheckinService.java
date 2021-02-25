package jp.kobe_u.root.shelter_navi.domain.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import jp.kobe_u.root.shelter_navi.domain.entity.Checkin;
import jp.kobe_u.root.shelter_navi.domain.exception.ShelterNaviException;
import jp.kobe_u.root.shelter_navi.domain.repository.CheckinRepository;
import jp.kobe_u.root.shelter_navi.domain.repository.ShelterRepository;
import jp.kobe_u.root.shelter_navi.domain.repository.UserRepository;

public class CheckinService {
    @Autowired
    UserRepository users;

    @Autowired
    ShelterRepository shelters;

    @Autowired
    CheckinRepository checkin_list;

    public Checkin checkin( String uid, Long sid ) {
        if ( ! users.existsById( uid ) ) {
            // User用のExceptionを投げる
            throw new ShelterNaviException( ShelterNaviException.USER_NOT_FOUND, "User: " + uid + " not found" );
        }

        if ( ! shelters.existsById( sid ) ) {
            // User用のExceptionを投げる
            throw new ShelterNaviException( ShelterNaviException.SHELTER_NOT_FOUND, "Shelter: " + sid + " not found" );
        }

        Checkin checkin = new Checkin( );
        return checkin_list.save( checkin );
    }
}
