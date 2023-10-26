package com.alibou.security.policy;

import com.alibou.security.common.entity.CommonDateEntity;
import com.alibou.security.role.Role;
import com.alibou.security.operation.Operation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Policy extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long policySeq;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_seq")
    private Role role;

//    @OneToMany(mappedBy = "policy")
//    private List<Privilege> privileges = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "privilege_seq")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Privilege> privileges;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_seq")
    private Operation operation;
}
