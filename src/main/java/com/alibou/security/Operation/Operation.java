package com.alibou.security.Operation;

import com.alibou.security.common.entity.CommonDateEntity;
import com.alibou.security.policy.Policy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Operation extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long operationSeq;

    private String name;

    @OneToMany(mappedBy = "operation")
    private List<Policy> policies = new ArrayList<>();
}
