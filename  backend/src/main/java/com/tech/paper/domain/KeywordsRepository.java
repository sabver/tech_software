package com.tech.paper.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordsRepository extends JpaRepository<Keywords, Long> {
    Page<Keywords> findByContentLikeOrderByScore(String content, Pageable pageable);
}
