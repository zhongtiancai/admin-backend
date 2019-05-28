package com.zhongtiancai.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @RequestMapping("/currentUser")
    public ResponseEntity currentUser(){
           return ResponseEntity.ok(null);
    }
}
