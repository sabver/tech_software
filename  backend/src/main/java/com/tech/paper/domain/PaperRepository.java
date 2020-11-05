package com.tech.paper.domain;

import com.tech.paper.jsonobj.PaperTitle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaperRepository extends JpaRepository<Paper, Long> {

    @Query(value = "select new com.tech.paper.jsonobj.PaperTitle(p.title) from Paper p")
    List<PaperTitle> findTitle();

    Page<Paper> findAll(Pageable pageable);

}
