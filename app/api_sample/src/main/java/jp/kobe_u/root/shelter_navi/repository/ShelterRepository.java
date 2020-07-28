package jp.kobe_u.root.shelter_navi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.root.shelter_navi.entity.Shelter;

@Repository
public interface ShelterRepository extends CrudRepository<Shelter, String> {
    
}