package com.tech.paper.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPaperRepository extends JpaRepository<UserPaper, Long> {
    List<UserPaper> findUserPaperByPaperEqualsAndUserEquals(Paper paper, User user);
    Page<UserPaper> findAll(Specification<UserPaper> specification, Pageable pageable);
}
