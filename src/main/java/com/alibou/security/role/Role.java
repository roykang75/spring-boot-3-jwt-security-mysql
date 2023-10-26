package com.alibou.security.role;

import com.alibou.security.common.entity.CommonDateEntity;
import com.alibou.security.common.enums.DeleteType;
import com.alibou.security.store.Store;
import com.alibou.security.policy.Policy;
import com.alibou.security.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleSeq;

    private String name;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(columnDefinition="tinyint(1) default 0")
    private int deleted;

    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    private List<Policy> policies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_seq")
    private Store store;

    public void update(String name, Level level) {
        this.name = name;
        this.level = level;
    }

    public void delete() {
        this.deleted = DeleteType.DELETED_YES.getValue();
    }

    @Transient
    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return authorities;
    }
}
