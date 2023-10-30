package com.alibou.security.policy;

import com.alibou.security.common.entity.CommonDateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Policy extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long policySeq;

    @Column(nullable = false, length = 100)
    private String name;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_seq")
//    private Role role;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Privilege> privileges = new ArrayList<>();

    @Builder
    public Policy(String name, List<Privilege> privileges) {
        this.name = name;
        if (privileges != null && !privileges.isEmpty()) {
            this.privileges.addAll(privileges);
        }
    }

}
