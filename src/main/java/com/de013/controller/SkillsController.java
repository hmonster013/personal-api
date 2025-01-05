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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.de013.dto.FilterVO;
import com.de013.model.Paging;
import com.de013.model.Skills;
import com.de013.service.SkillsService;
import com.de013.utils.URI;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URI.V1 + URI.SKILLS)
public class SkillsController extends BaseController{
    static Logger log = LoggerFactory.getLogger(SkillsController.class.getName());

    @Autowired
    private SkillsService skillsService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listSkills(@RequestBody FilterVO request) throws Exception {
        log.info("Search Skill");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Skills> result = skillsService.search(request, paging);
        log.info("Search skills total elements [" + result.getTotalElements() + "]");
        List<Skills> contentList = result.getContent();
        
        ResponseEntity test = responseList(contentList, result);
        return responseList(contentList, result);
    }
}
