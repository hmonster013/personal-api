package com.de013.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.de013.dto.FilterVO;
import com.de013.dto.ProjectsRequest;
import com.de013.dto.ProjectsVO;
import com.de013.exception.RestException;
import com.de013.model.Paging;
import com.de013.model.Projects;
import com.de013.service.ProjectsService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.PROJECTS)
public class ProjectsController extends BaseController{
    static Logger log = LoggerFactory.getLogger(ProjectsController.class.getName());

    @Autowired
    private ProjectsService projectsService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listProjects(@RequestBody FilterVO request) throws Exception {
        log.info("Search projects");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Projects> result = projectsService.search(request, paging);
        log.info("Search project total elements [" + result.getTotalElements() + "]");
        List<ProjectsVO> reponseList = result.stream().map(Projects::getVO).toList();
        
        return responseList(reponseList, result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") long id) throws Exception{
        log.info("Get detail project by [" + id + "]");
        
        Projects project = projectsService.findById(id);
		log.info("Get project by getId [" + id + "]");
		if (project != null) {
			return response(project.getVO());
		} else {
			throw new RestException("Project Id [" + id + "] invalid ");
		}
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createProject(@ModelAttribute ProjectsRequest projectRequest) throws Exception {
        log.info("Create project");
        
        Projects project = projectsService.create(projectRequest);
        return response(project.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProject(@ModelAttribute ProjectsRequest projectRequest) throws Exception {
        log.info("Start update project");

        Long id = projectRequest.getId();
        Projects existed = projectsService.findById(id);

        if (existed == null) {
			throw new RestException("Project Id [" + id + "] invalid ");
		}

        Projects result = projectsService.update(projectRequest, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete project:[" + id + "]");

        Projects existed = projectsService.findById(id);
        if (existed == null) {
            throw new RestException("Project id [" + id + "] invailid");
        }

        projectsService.deleteById(id);
        return responseMessage("Deleted project [" + id + "] sucessfully");
    }
}
