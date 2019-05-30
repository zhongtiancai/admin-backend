package com.zhongtiancai.admin.service;

import com.zhongtiancai.admin.dao.PermissionRepository;
import com.zhongtiancai.admin.dao.RoleRepository;
import com.zhongtiancai.admin.entity.Admin;
import com.zhongtiancai.admin.entity.Permission;
import com.zhongtiancai.admin.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by zhongtiancai on 2019/5/28.
 */
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * 保存角色
     * @param role 角色
     * @param permissionIds 权限
     * @return
     */
    public Role save(Role role, Set<Long> permissionIds) {
        role.setStatus(1);
        if(!CollectionUtils.isEmpty(permissionIds)){
            List<Permission> permissions = permissionRepository.findAllById(permissionIds);
            role.setPermissionList(permissions);
        }
        return  roleRepository.save(role);
    }

    /**
     * 更新角色权限
     * @param id 角色id
     * @param permissionIds 权限id
     * @return
     */
    public Role update(Long id, Set<Long> permissionIds) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (!CollectionUtils.isEmpty(permissionIds)) {
            if (roleOpt.isPresent()) {
                List<Permission> permissions = permissionRepository.findAllById(permissionIds);
                Role role = roleOpt.get();
                role.setPermissionList(permissions);
                return roleRepository.save(role);
            }
        }
        //TODO 定义一些业务异常
        throw new RuntimeException();

    }

    /**
     * 获取角色详情
     * @param id 角色id
     * @return
     */
    public Role getRoleById(Long id) {
        Optional<Role> optional = roleRepository.findById(id);
        return optional.isPresent()?optional.get():null;
    }

    /**
     * 根据名字搜索权限
     * @param name 名字
     * @param page 页数
     * @param pageSize 每页长度
     * @return
     */
    public Page<Role> searchByName(String name, Integer page,Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1,pageSize, Sort.Direction.DESC,"id");
        if(StringUtils.isEmpty(name)){
            return roleRepository.findAll(pageRequest);
        }
        return  roleRepository.findByNameLike("%"+name+"%",pageRequest);
    }

    public List<Role> findByStatus(int status) {
       return roleRepository.findByStatus(status);
    }
}
