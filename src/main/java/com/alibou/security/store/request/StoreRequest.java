package com.alibou.security.store.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StoreRequest {
    private String name;
    private String telephone;
    private String mobile;
    private String email;
    private String registration;
}
