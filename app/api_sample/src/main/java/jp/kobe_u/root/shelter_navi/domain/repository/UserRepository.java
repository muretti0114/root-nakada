package jp.kobe_u.root.shelter_navi.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.root.shelter_navi.domain.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
    
}
