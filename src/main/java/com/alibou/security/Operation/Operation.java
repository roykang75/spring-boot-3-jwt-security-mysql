package com.alibou.security.operation;

import com.alibou.security.common.entity.CommonDateEntity;
import com.alibou.security.policy.Policy;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import com.alibou.security.advice.exception.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.ToString;


@ToString
@NoArgsConstructor
@Entity
public class Operation extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long operationSeq;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Policy> policies = new ArrayList<>();

    @Builder
    public Operation(String name, List<Policy> policies) {
        this.name = name;
        if (policies != null && !policies.isEmpty()) {
            this.policies.addAll(policies);
        }
    }
}
