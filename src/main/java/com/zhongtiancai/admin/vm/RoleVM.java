package com.zhongtiancai.admin.vm;


import com.zhongtiancai.admin.entity.Admin;

import java.util.Set;

/**
 * Created by zhongtiancai on 2019/5/28.
 */

public class RoleVM  {

    private Long id;
    private String name;
    private String description;
    private Set<Long> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Long> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Long> permissions) {
        this.permissions = permissions;
    }
}
