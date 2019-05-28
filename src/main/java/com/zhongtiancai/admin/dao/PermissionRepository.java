package com.zhongtiancai.admin.dao;

import com.zhongtiancai.admin.entity.Permission;
import com.zhongtiancai.admin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhongtiancai on 2019/5/28.
 */
public interface PermissionRepository extends JpaRepository<Permission,Long> {


    List<Permission> findByStatus(int status);
}
