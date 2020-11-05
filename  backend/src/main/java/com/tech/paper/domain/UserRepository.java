package com.tech.paper.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByAccountEqualsAndPasswordEquals(String account,String password);
    List<User> findByAccountEquals(String account);
    List<User> findByIdEquals(Long id);
}
