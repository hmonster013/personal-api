package com.de013.controller;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.de013.config.DateConfig;
import com.de013.dto.ExperiencesRequest;
import com.de013.dto.ExperiencesVO;
import com.de013.dto.FilterVO;
import com.de013.dto.ProjectsRequest;
import com.de013.exception.RestException;
import com.de013.model.Experiences;
import com.de013.model.Paging;
import com.de013.service.ExperiencesService;
import com.de013.utils.URI;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(URI.V1 + URI.EXPERIENCES)
public class ExperiencesController extends BaseController{
    static Logger log = LoggerFactory.getLogger(ExperiencesController.class.getName());

    @Autowired
    private ExperiencesService experiencesService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listExperiences(@RequestBody FilterVO request) throws Exception {
        log.info("Search experiences");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Experiences> result = experiencesService.search(request, paging);
        log.info("Search experiences total elements [" + result.getTotalElements() + "]");
        List<ExperiencesVO> reponseList = result.stream().map(Experiences::getVO).toList();
        
        return responseList(reponseList, result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") long id) throws Exception{
        log.info("Get detail experiences");
        
        Experiences experience = experiencesService.findById(id);
		log.info("Get experience by getId [" + id + "]");
		if (experience != null) {
			return response(experience.getVO());
		} else {
			throw new RestException("Experiences Id [" + id + "] invalid ");
		}
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createExperience(
        @RequestParam("requestJsonData") String jsonData,
        @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
        log.info("Create experience");

        ObjectMapper objectMapper = new ObjectMapper();
        ExperiencesRequest experienceRequest = objectMapper.readValue(jsonData, ExperiencesRequest.class);
        Experiences experience = experiencesService.create(experienceRequest, file);
        return response(experience.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateExperience(
        @RequestParam("requestJsonData") String jsonData,
        @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
        log.info("Start update experience");

        ObjectMapper objectMapper = new ObjectMapper();
        ExperiencesRequest experienceRequest = objectMapper.readValue(jsonData, ExperiencesRequest.class);
        
        Long id = experienceRequest.getId();
        Experiences existed = experiencesService.findById(id);

        if (existed == null) {
			throw new RestException("Experience Id [" + id + "] invalid ");
		}

        Experiences result = experiencesService.update(experienceRequest, existed, file);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete experience" + id);

        Experiences existed = experiencesService.findById(id);
        if (existed == null) {
            throw new RestException("Experiences id [" + id + "] invailid");
        }

        experiencesService.deleteById(id);
        return responseMessage("Deleted experiences [" + id + "] sucessfully");
    }
}
