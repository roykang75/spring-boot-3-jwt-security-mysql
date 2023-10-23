package com.alibou.security.policy;

import com.alibou.security.policy.Policy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long privilegeSeq;

    private int pCreate;
    private int pRead;
    private int pUpdate;
    private int pDelete;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "policy_seq")
    private Policy policy;

}
