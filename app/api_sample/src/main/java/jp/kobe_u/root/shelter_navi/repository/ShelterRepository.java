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

    @Query(
        value = "SELECT *,( 6371 * acos( cos( radians( ?2 ) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians( ?1 ) ) + sin( radians( ?2 ) ) * sin( radians( lat ) ) ) ) AS distance FROM shelter HAVING distance <= ?3 ORDER BY distance",
        nativeQuery = true
    )
    public Iterable<Shelter> findShelterByDistance( Double userLng, Double userLat, Double distance );

    @Query(
        value = "SELECT * FROM shelter s WHERE s.name like %?1% OR s.address like %?1%",
        nativeQuery = true
    )
    public Iterable<Shelter> findShelterByKeyword( String keyword );
}