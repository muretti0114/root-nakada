package jp.kobe_u.root.shelter_navi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.root.shelter_navi.repository.ShelterRepository;

@Service
public class ShelterNaviService {
    
    @Autowired
    private ShelterRepository shelters;

    
}