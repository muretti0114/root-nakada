package jp.kobe_u.root.shelter_navi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.root.shelter_navi.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
    
}
