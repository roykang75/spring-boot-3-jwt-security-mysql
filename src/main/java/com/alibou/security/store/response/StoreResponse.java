package com.alibou.security.store.response;

import com.alibou.security.store.Store;
import lombok.Getter;

@Getter
public class StoreResponse {
    private long seq;
    private String id;

    private String name;
    private String telephone;
    private String mobile;
    private String email;
    private String registration;

    public StoreResponse(Store store) {
        this.seq = store.getSeq();
        this.id = store.getId();
        this.name = store.getName();
        this.telephone = store.getTelephone();
        this.mobile = store.getMobile();
        this.email = store.getEmail();
        this.registration = store.getRegistration();
    }
}
