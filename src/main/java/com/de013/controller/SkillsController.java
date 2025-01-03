package com.de013.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.de013.dto.FilterVO;
import com.de013.service.SkillsService;
import com.de013.utils.URI;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(URI.V1 + URI.SKILLS)
public class SkillsController extends BaseController{
    static Logger log = LoggerFactory.getLogger(SkillsController.class.getName());

    @Autowired
    private SkillsService skillsService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listSkills(@RequestBody FilterVO request) throws Exception {
        return response("TEST", "123");
    }
    
    @GetMapping("test")
    public String getMethodName() {
        return new String("HELLO");
    }
    
}
