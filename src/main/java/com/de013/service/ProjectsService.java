package com.de013.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.FilterVO;
import com.de013.dto.ProjectsRequest;
import com.de013.model.Projects;
import com.de013.repository.ProjectsRepository;
import com.de013.utils.Utils;

@Service
public class ProjectsService {
    private static final Logger log = LoggerFactory.getLogger(ProjectsService.class.getSimpleName());

    @Autowired
    private ProjectsRepository projectsRepository;

    public List<Projects> findAll() {
        return projectsRepository.findAll();
    }

    public Projects findById(Long id) {
        return projectsRepository.findById(id).orElse(null);
    }

    public Projects create(ProjectsRequest request) {
        Projects projects = new Projects(request);
        this.save(projects);
        return projects;
    }

    public Projects update(ProjectsRequest request, Projects existed) {
        log.debug("update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = save(existed);
        return existed;
    }

    public Projects save(Projects projects) {
        projects = projectsRepository.save(projects);
        log.debug("save " + projects);
        return projects;
    }

    public void deleteById(Long id) {
        projectsRepository.deleteById(id);
    }

    public Page<Projects> search(FilterVO request, Pageable paging) {
        return projectsRepository.search(request, paging);
    }
}
