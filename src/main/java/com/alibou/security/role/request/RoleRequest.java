package com.alibou.security.role.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoleRequest {
    private String storeId;
    private String name;
    private String level;
}
