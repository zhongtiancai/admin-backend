package com.zhongtiancai.admin.controller;

import com.zhongtiancai.admin.entity.Admin;
import com.zhongtiancai.admin.service.AdminService;
import com.zhongtiancai.admin.vm.AdminVM;
import com.zhongtiancai.admin.vm.CommonPage;
import com.zhongtiancai.admin.vm.RoleVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by zhongtiancai on 2019/5/28.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/save")
    public ResponseEntity addAdmin(@RequestBody AdminVM adminVM){
        Admin admin = new Admin();
        admin.setPassword(adminVM.getPassword());
        admin.setUsername(adminVM.getUsername());
        adminService.save(admin, adminVM.getRoles());
        return ResponseEntity.ok(null);
    }

    @RequestMapping("/update")
    public ResponseEntity updateAdmin(@RequestBody AdminVM adminVM){
        adminService.update(adminVM.getId(), adminVM.getRoles());
        return ResponseEntity.ok(null);
    }


    @RequestMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        Admin admin = adminService.getAdminById(id);
        //TODO  nullCheck
        AdminVM adminVM = new AdminVM();
        BeanUtils.copyProperties(admin,adminVM);
        adminVM.setPassword("");
        adminVM.setRoles(admin.getRoleList().stream().map(role->role.getId()).collect(Collectors.toSet()));
        return ResponseEntity.ok(adminVM);
    }


    @RequestMapping("/findPage")
    public ResponseEntity findPage(AdminVM adminVM, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize){
        Page<Admin> pageResult = adminService.searchByNickName(adminVM.getNickName(),page,pageSize);
        pageResult.getContent().forEach(admin->{admin.setPassword(null);admin.setRoleList(new ArrayList<>());});
        CommonPage commonPage = CommonPage.of(pageResult);
        return ResponseEntity.ok(commonPage);
    }

}
