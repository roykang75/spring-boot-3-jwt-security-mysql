package com.alibou.security.policy;

import com.alibou.security.role.Role;
import com.alibou.security.Operation.Operation;
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
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long policySeq;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_seq")
    private Role role;

    @OneToMany(mappedBy = "policy")
    private List<Privilege> privileges = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_seq")
    private Operation operation;
}
