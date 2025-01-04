package com.de013.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.de013.utils.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(URI.V1 + URI.TEST)
public class TestController {
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
}