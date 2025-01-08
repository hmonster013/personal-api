package com.de013.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.de013.dto.ExperiencesRequest;
import com.de013.model.Experiences;
import com.de013.utils.URI;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(URI.V1 + URI.TEST)
public class TestController extends BaseController{
  static Logger log = LoggerFactory.getLogger(TestController.class.getName());
    
  @GetMapping(value = URI.ALL, produces = MediaType.APPLICATION_JSON_VALUE)
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping(value = URI.USER)
  @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping(value = URI.MOD)
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping(value = URI.ADMIN)
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }

  @PostMapping("/custom")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity testCustomDateString(@RequestBody ExperiencesRequest entity) {
    log.info(entity.getStartDate().toString());
    log.info(entity.getEndDate().toString());
    return response(entity);
  }
  
}