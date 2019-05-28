package com.zhongtiancai.admin.service;

import com.zhongtiancai.admin.dao.PermissionRepository;
import com.zhongtiancai.admin.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhongtiancai on 2019/5/28.
 */
@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> findByStatus(int status) {
       return  permissionRepository.findByStatus(status);
    }
}
