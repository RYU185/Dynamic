package com.dw.dynamic.repository;

import com.dw.dynamic.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
    public List<Authority> findByAuthorityName(String name);

}