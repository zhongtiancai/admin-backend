package com.zhongtiancai.admin.dao;

import com.zhongtiancai.admin.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by zhongtiancai on 2019/5/28.
 */
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findByUsername(String username);

    Page<Admin> findByNickNameLike(String nickName, Pageable pageable);
}
