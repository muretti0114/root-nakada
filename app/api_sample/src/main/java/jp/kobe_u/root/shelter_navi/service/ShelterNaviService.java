package jp.kobe_u.root.shelter_navi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.root.shelter_navi.dto.ShelterDto;
import jp.kobe_u.root.shelter_navi.entity.Shelter;
import jp.kobe_u.root.shelter_navi.exception.ShelterNotFoundException;
import jp.kobe_u.root.shelter_navi.repository.ShelterRepository;

@Service
public class ShelterNaviService {
    
    @Autowired
    private ShelterRepository shelters;

    /*
    public Shelter createShelter( ShelterDto shelter_dto ) {
        // 避難所のidをシステム側で生成する場合，何を基準に登録済みか判定するのか？
        // 避難所の名前？
        String shelter_name = shelter_dto.getName();

        // 同名の避難所がないかチェック？
        // 全国的に考えると同名の避難所はありそう
        // 自治体コードと同名ともに同じ場合のみはじく？
        if ( shelters.findShelterByName( shelter_name ).isPresent() ) {
            throw new ShelterValidationException();
        }

        // dtoの自治体コードを基に通番の最大値を取ってくる(id)
        // 取得した通番に1を足して自治体コードを併せたidに変換
        // idとdtoを基にShelterを生成
        Shelter shelter = new Shelter();
    }
    */

    public List<Shelter> getAllShelters() {
        List<Shelter> shelter_list = new ArrayList<Shelter>();
        Iterable<Shelter> all_shelters = shelters.findAll();

        all_shelters.forEach( shelter_list::add );

        return shelter_list;
    }

    public Shelter getShelter( Long id ) {
        Shelter shelter = shelters.findById( id ).orElseThrow(
            () -> new ShelterNotFoundException( ShelterNotFoundException.ACCOUNT_NOT_FOUND, "Shelter : " + id + " is not found" ) 
        );

        return shelter;
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