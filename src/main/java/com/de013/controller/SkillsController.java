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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.de013.dto.FilterVO;
import com.de013.dto.SkillsRequest;
import com.de013.dto.SkillsVO;
import com.de013.exception.RestException;
import com.de013.model.Paging;
import com.de013.model.Skills;
import com.de013.service.SkillsService;
import com.de013.utils.URI;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URI.V1 + URI.SKILLS)
public class SkillsController extends BaseController{
    static Logger log = LoggerFactory.getLogger(SkillsController.class.getName());

    @Autowired
    private SkillsService skillsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listSkills(@RequestBody FilterVO request) throws Exception {
        log.info("Search Skill");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Skills> result = skillsService.search(request, paging);
        log.info("Search skills total elements [" + result.getTotalElements() + "]");
        List<SkillsVO> reponseList = result.stream().map(Skills::getVO).toList();
        
        return responseList(reponseList, result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") long id) throws Exception{
        log.info("Get detail skill");
        
        Skills skills = skillsService.findById(id);
		log.info("Get skill by getId [" + id + "]");
		if (skills != null) {
			return response(skills.getVO());
		} else {
			throw new RestException("Skill Id [" + id + "] invalid ");
		}
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createSkill(@ModelAttribute SkillsRequest skillsRequest) throws Exception {
        log.info("Create skill");
        Skills skills = skillsService.create(skillsRequest);
        return response(skills);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateSkill(@ModelAttribute SkillsRequest skillsRequest) throws Exception {
        log.info("Start update skill");

        Long id = skillsRequest.getId();
        Skills existed = skillsService.findById(id);

        if (existed == null) {
			throw new RestException("Skill Id [" + id + "] invalid ");
		}

        Skills result = skillsService.update(skillsRequest, existed);
        return response(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete skill" + id);

        Skills existed = skillsService.findById(id);
        if (existed == null) {
            throw new RestException("Skill id [" + id + "] invailid");
        }

        skillsService.deleteById(id);
        return responseMessage("Deleted Skill [" + id + "] sucessfully");
    }
}
