package com.de013.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Links;

@Repository
public interface LinksRepository extends JpaRepository<Links, Long> {
    @Query("SELECT s FROM Links s WHERE 1=1 "
        + " AND ((:#{#p.name}) IS NULL OR LOWER(s.name) LIKE CONCAT('%', LOWER(:#{#p.name}), '%') )"
        + " ORDER BY s.id ASC ")
    public Page<Links> search(@Param("p") FilterVO request, Pageable paging);
}
