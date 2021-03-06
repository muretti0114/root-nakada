package jp.kobe_u.root.shelter_navi.domain.service;

import java.util.ArrayList;
import java.util.List;
//import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.root.shelter_navi.domain.entity.Shelter;
import jp.kobe_u.root.shelter_navi.domain.exception.ShelterNotFoundException;
import jp.kobe_u.root.shelter_navi.domain.exception.ShelterValidationException;
//import jp.kobe_u.root.shelter_navi.form.SearchByDistanceForm;
//import jp.kobe_u.root.shelter_navi.form.SearchByKeywordForm;
import jp.kobe_u.root.shelter_navi.application.form.ShelterForm;
import jp.kobe_u.root.shelter_navi.domain.repository.ShelterRepository;

@Service
public class ShelterService {
    
    @Autowired
    private ShelterRepository shelters;

    public Shelter createShelter( ShelterForm form ) {
        Long id = form.getId();

        // 同名の避難所がないかチェック？
        // 全国的に考えると同名の避難所はありそう
        // 自治体コードと同名ともに同じ場合のみはじく？
        if ( shelters.findById( id ).isPresent() ) {
            throw new ShelterValidationException( ShelterValidationException.SHELTER_ALREADY_EXISTS, "shelter_id : " + id + "is already exists" );
        }

        // dtoの自治体コードを基に通番の最大値を取ってくる(id)
        // 取得した通番に1を足して自治体コードを併せたidに変換
        // idとdtoを基にShelterを生成
        Shelter shelter = shelters.save( form.createShelter() );
        return shelter;
    }

    public List<Shelter> getAllShelters() {
        List<Shelter> shelter_list = new ArrayList<Shelter>();
        Iterable<Shelter> all_shelters = shelters.findAll();

        all_shelters.forEach( shelter_list::add );

        if ( shelter_list.isEmpty() ) throw new ShelterNotFoundException( ShelterNotFoundException.ACCOUNT_NOT_FOUND, "Shelters are not found." );
        
        return shelter_list;
    }

    public Shelter getShelter( Long id ) {
        Shelter shelter = shelters.findById( id ).orElseThrow(
            () -> new ShelterNotFoundException( ShelterNotFoundException.ACCOUNT_NOT_FOUND, "Shelter : " + id + " is not found" ) 
        );

        return shelter;
    }

    public List<Shelter> searchSheltersByDistance( Double userLng, Double userLat, Double distance ) {
        Iterable<Shelter> all_shelters = shelters.findShelterByDistance( userLng, userLat, distance );

        List<Shelter> shelter_list = new ArrayList<Shelter>();

        all_shelters.forEach( shelter_list::add );

        if ( shelter_list.isEmpty() ) throw new ShelterNotFoundException( ShelterNotFoundException.ACCOUNT_NOT_FOUND, "Shelters in " + distance + "km are not found." );
        
        return shelter_list;
    }

    public List<Shelter> searchSheltersByKeyword( String keyword ) {
        Iterable<Shelter> all_shelters = shelters.findShelterByKeyword( keyword );

        List<Shelter> shelter_list = new ArrayList<Shelter>();

        all_shelters.forEach( shelter_list::add );

        if ( shelter_list.isEmpty() ) throw new ShelterNotFoundException( ShelterNotFoundException.ACCOUNT_NOT_FOUND, "There is no shelter that includes " + keyword + " in its name or address." );
        
        return shelter_list;
    }

    public void deleteShelter( Long id ) {
        Shelter shelter = shelters.findById( id ).orElseThrow(
            () -> new ShelterNotFoundException( ShelterNotFoundException.ACCOUNT_NOT_FOUND, "Shelter : " + id + " is not found" )
        );

        shelters.deleteById( shelter.getId() );
    }

    public void clearAllShelters() {
        shelters.deleteAll();
    }
}