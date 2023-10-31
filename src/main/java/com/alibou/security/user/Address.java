package com.alibou.security.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String zipCode;
    private String address1;
    private String address2;
}
