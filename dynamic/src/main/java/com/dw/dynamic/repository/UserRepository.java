package com.dw.dynamic.repository;

import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    List<User> findByRealNameLike(String realName);

    List<User> findUserByExistBusinessOperator(boolean existBusinessOperator);

    User findByEmail(String email);
}
