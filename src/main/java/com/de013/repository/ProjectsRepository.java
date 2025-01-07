package com.de013.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Projects;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    @Query("SELECT s FROM Projects s WHERE 1=1 ")
    public Page<Projects> search(@Param("p") FilterVO request, Pageable paging);
}
