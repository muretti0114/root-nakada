package jp.kobe_u.root.shelter_navi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.root.shelter_navi.entity.Shelter;

@Repository
public interface ShelterRepository extends CrudRepository<Shelter, Long> {
    
    @Query(
        value = "SELECT * from shelter s where s.name = ?1 LIMIT 1",
        nativeQuery = true
    )
    Optional<Shelter> findShelterByName( String name );
}