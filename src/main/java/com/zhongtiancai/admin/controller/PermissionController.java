package com.zhongtiancai.admin.controller;

import com.zhongtiancai.admin.entity.Permission;
import com.zhongtiancai.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhongtiancai on 2019/5/28.
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/all")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(permissionService.findByStatus(1));
    }
}
