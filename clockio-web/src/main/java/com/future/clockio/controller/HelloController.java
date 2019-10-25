package com.future.clockio.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
  @GetMapping("/hello")
  public String printHello(){
    return "Hello World!";
  }

  @GetMapping("/about")
  public String printAbout() {return "This is an about page!"; }

  @GetMapping("/test")
  public Map<String,String> printTest() {
    Map<String,String> test = new HashMap<>();
    test.put("test", "Hasil dari Test");
    return test;
  }

  @GetMapping("/api/x")
  public String apix() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return "You are here! after Bearer Authorization!" + auth.getName();
  }

  @GetMapping("/test")
  public Map<String,String> printTest() {
    Map<String,String> test = new HashMap<>();
    test.put("test", "Hasil dari Test");
    return test;
  }

  @GetMapping("/api/profile")
  public Object getProfile() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth.getPrincipal();
  }
}
