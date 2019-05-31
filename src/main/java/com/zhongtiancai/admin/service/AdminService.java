package com.zhongtiancai.admin.service;

import com.zhongtiancai.admin.dao.AdminRepository;
import com.zhongtiancai.admin.dao.PermissionRepository;
import com.zhongtiancai.admin.dao.RoleRepository;
import com.zhongtiancai.admin.entity.Admin;
import com.zhongtiancai.admin.entity.Permission;
import com.zhongtiancai.admin.entity.Role;
import com.zhongtiancai.admin.vm.AdminUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhongtiancai on 2019/5/28.
 */
@Service
public class AdminService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public AdminUserDetails getAdminByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            AdminUserDetails adminDetail = new AdminUserDetails(admin);
            if(admin.getUsername().equals("admin")){
                List<Permission> permissions = permissionRepository.findAll();
                adminDetail.addPermissions(permissions);
            }
            return adminDetail;
        }
        return null;
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    /**
     * 保存用户
     * @param adminParams 用户信息
     * @param roleIds 角色id
     * @return
     */
    @Transactional
    public Admin save(Admin adminParams, Set<Long> roleIds) {
        Admin adminDb = new Admin();

        if (adminParams.getId() != null) {
            adminDb = adminRepository.findById(adminParams.getId()).get();
        } else {
            String password = passwordEncoder.encode(adminParams.getPassword());
            adminDb.setPassword(password);
            adminDb.setStatus(1);
            adminDb.setUsername(adminParams.getUsername());
        }

        adminDb.setEmail(adminParams.getEmail());
        adminDb.setNickName(adminParams.getNickName());

        if (!CollectionUtils.isEmpty(roleIds)) {
            List<Role> role = roleRepository.findAllById(roleIds);
            adminDb.setRoleList(role);
        }
        return adminRepository.save(adminDb);
    }




    /**
     * 根据id获取用户信息
     * @param id 用户id
     * @return
     */
    public Admin getAdminById(Long id) {
        Optional<Admin> optional = adminRepository.findById(id);
        return optional.isPresent()?optional.get():null;
    }

    public Page<Admin> searchByNickName(String nickName, Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1,pageSize, Sort.Direction.DESC,"id");
        if(StringUtils.isEmpty(nickName)){
            return adminRepository.findAll(pageRequest);
        }
        return  adminRepository.findByNickNameLike("%"+nickName+"%",pageRequest);
    }
}
