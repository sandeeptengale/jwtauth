package com.sandeep.auth.JwtAuth.repository;

import com.sandeep.auth.JwtAuth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserName(String userName);
    User findByUserName(String userName);
    @Transactional
    void deleteByUserName(String userName);
}
