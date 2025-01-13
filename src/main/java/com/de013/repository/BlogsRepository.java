package com.de013.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Blogs;
import com.de013.model.Projects;

@Repository
public interface BlogsRepository extends JpaRepository<Blogs, Long>{
    @Query("SELECT s FROM Blogs s WHERE 1=1 "
        + " AND ((:#{#p.title}) IS NULL OR LOWER(s.title) LIKE CONCAT('%', LOWER(:#{#p.title}), '%') ) "
        + " ORDER BY s.id ASC ")
    public Page<Blogs> search(@Param("p") FilterVO request, Pageable paging);
}
