package com.alibou.security.apifunc;

import com.alibou.security.common.entity.CommonDateEntity;
import com.alibou.security.policy.Policy;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;


@ToString
@Getter
@NoArgsConstructor
@Entity
public class ApiFunc extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String path;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Policy> policies = new ArrayList<>();

    @Builder
    public ApiFunc(String name, String path, List<Policy> policies) {
        this.name = name;
        this.path = path;
        if (policies != null && !policies.isEmpty()) {
            this.policies.addAll(policies);
        }
    }
}
