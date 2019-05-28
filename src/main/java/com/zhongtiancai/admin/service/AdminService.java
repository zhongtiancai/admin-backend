package com.zhongtiancai.admin.service;

import com.zhongtiancai.admin.dao.AdminRepository;
import com.zhongtiancai.admin.dao.RoleRepository;
import com.zhongtiancai.admin.entity.Admin;
import com.zhongtiancai.admin.entity.Role;
import com.zhongtiancai.admin.vm.AdminUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Transactional
    public AdminUserDetails getAdminByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            return new AdminUserDetails(admin);
        }
        return null;
    }

    /**
     * 保存用户
     * @param admin 用户信息
     * @param roleIds 角色id
     * @return
     */
    @Transactional
    public Admin save(Admin admin, Set<Long> roleIds) {
        String password = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(password);
        admin.setStatus(1);
        if(!CollectionUtils.isEmpty(roleIds)){
           List<Role>  role = roleRepository.findAllById(roleIds);
           admin.setRoleList(role);
        }
        return  adminRepository.save(admin);
    }

    /**
     * 更新用户角色
     * @param id 用户id
     * @param roles 用户角色
     * @return
     */
    public Admin update(Long id, Set<Long> roles) {
        Optional<Admin> adminOpt = adminRepository.findById(id);
        if (!CollectionUtils.isEmpty(roles)) {
            if (adminOpt.isPresent()) {
                List<Role> role = roleRepository.findAllById(roles);
                Admin admin = adminOpt.get();
                admin.setRoleList(role);
                return adminRepository.save(admin);
            }
        }
        //TODO 定义一些业务异常
        throw new RuntimeException();
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
        PageRequest pageRequest = PageRequest.of(page - 1,pageSize, Sort.Direction.DESC);
        return  adminRepository.findByNickNameLike(nickName,pageRequest);
    }
}
