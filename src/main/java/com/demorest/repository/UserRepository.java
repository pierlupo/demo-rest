package com.demorest.repository;

import com.demorest.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

User findUserByUsername(String userName);
}
