package com.zhongtiancai.admin.vm;

import com.zhongtiancai.admin.entity.Admin;
import com.zhongtiancai.admin.entity.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户详情
 */
public class AdminUserDetails implements UserDetails {
    private Admin admin;
    private Set<SimpleGrantedAuthority> authorities;

    public AdminUserDetails(Admin admin) {
        this.admin = admin;
        this.authorities = admin.getRoleList().stream().flatMap(role->role.getPermissionList().stream()).map(permission -> new SimpleGrantedAuthority(permission.getKey())).collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return  this.authorities;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return admin.getStatus().equals(1);
    }

    public String getNickName(){
        return admin.getNickName();
    }
    public String getIcon(){
        return admin.getIcon();
    }


    public void addPermissions(List<Permission> permissions) {
       Set authorities = permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.getKey())).collect(Collectors.toSet());
        if(this.authorities == null){
            this.authorities = authorities;
        }else{
            this.authorities.addAll(authorities);
        }
    }
}
