package com.alibou.security.policy;

import com.alibou.security.common.entity.CommonDateEntity;
import com.alibou.security.policy.Policy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Privilege extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long privilegeSeq;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition="tinyint(1) default 0")
    private int deleted;

    @Builder
    public Privilege(String name) {
        this.name = name;
    }


}
