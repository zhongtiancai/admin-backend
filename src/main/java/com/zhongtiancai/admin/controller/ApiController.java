package com.zhongtiancai.admin.controller;

import com.zhongtiancai.admin.vm.AdminUserDetails;
import com.zhongtiancai.admin.vm.AdminVM;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @RequestMapping("/currentUser")
    public ResponseEntity currentUser(){
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AdminVM adminVm = new AdminVM();
        BeanUtils.copyProperties(adminUserDetails,adminVm);
        adminVm.setPassword("");
        return ResponseEntity.ok(adminVm);
    }
}
