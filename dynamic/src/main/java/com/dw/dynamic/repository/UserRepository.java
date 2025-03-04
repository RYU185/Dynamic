package com.dw.dynamic.repository;

import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUserName(String userName);

    List<User> findByRealNameLike(String realName);

    List<User> findUserByExistBusinessOperator(boolean existBusinessOperator);

    List<User> findByEmail(String email);

    @Query("select u.existBusinessOperator from User u where userName =:userName")
    boolean findByBusinessOperator(String userName);
}
