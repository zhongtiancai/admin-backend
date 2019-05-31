package com.zhongtiancai.admin.controller;

import com.zhongtiancai.admin.entity.Admin;
import com.zhongtiancai.admin.entity.Role;
import com.zhongtiancai.admin.service.AdminService;
import com.zhongtiancai.admin.service.RoleService;
import com.zhongtiancai.admin.vm.AdminVM;
import com.zhongtiancai.admin.vm.CommonPage;
import com.zhongtiancai.admin.vm.RoleVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * Created by zhongtiancai on 2019/5/28.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/save")
    public ResponseEntity addRole(@RequestBody RoleVM roleVM){
        Role role = new Role();
        BeanUtils.copyProperties(roleVM,role);
        roleService.save(role, roleVM.getPermissions());
        return ResponseEntity.ok(role);
    }


    @RequestMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        Role role = roleService.getRoleById(id);
        //TODO  nullCheck
        RoleVM roleVm = new RoleVM();
        BeanUtils.copyProperties(role,roleVm);
        roleVm.setPermissions(role.getPermissionList().stream().map(permission -> permission.getId()).collect(Collectors.toSet()));
        return ResponseEntity.ok(roleVm);
    }

    @RequestMapping("/findPage")
    public ResponseEntity findPage(RoleVM roleVM, @RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer pageSize){
        Page<Role> rolePage = roleService.searchByName(roleVM.getName(), page, pageSize);
        return ResponseEntity.ok(CommonPage.of(rolePage));
    }

    @RequestMapping("/all")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(roleService.findByStatus(1));
    }

    
}
