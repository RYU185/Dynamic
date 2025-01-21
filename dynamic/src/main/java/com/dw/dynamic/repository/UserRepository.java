package com.dw.dynamic.repository;

import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUserName(String userName);

    List<User> findByRealNameLike(String realName);

    List<User> findUserByExistBusinessOperator(boolean existBusinessOperator);

    User findByEmail(String email);
}
