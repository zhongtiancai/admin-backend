package com.zhongtiancai.admin.dao;

import com.zhongtiancai.admin.entity.Admin;
import com.zhongtiancai.admin.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhongtiancai on 2019/5/28.
 */
public interface RoleRepository extends JpaRepository<Role,Long> {


    Page<Role> findByNameLike(String name, Pageable pageable);


    List<Role> findByStatus(int status);
}
