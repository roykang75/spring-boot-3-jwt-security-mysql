package com.alibou.security.role.response;

import com.alibou.security.role.Role;
import lombok.Getter;

@Getter
public class RoleResponse {
    private long seq;
    private String name;
    private String level;

    public RoleResponse(Role role) {
        this.seq = role.getRoleSeq();
        this.name = role.getName();
        this.level = role.getLevel().name();
    }
}
