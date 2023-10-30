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



@ToString
@Getter
@NoArgsConstructor
@Entity
public class Role extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleSeq;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(columnDefinition="tinyint(1) default 0")
    private int deleted;


//    @OneToMany(fetch = FetchType.EAGER)
//    private List<User> users = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<Policy> policies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_seq")
    private Store store;

    @Builder
    public Role(String name, String level, Store store, List<Policy> policies) {
        this.name = name;
        this.level = Level.getLevel(level);
        this.store = store;

        if (policies != null && !policies.isEmpty()) {
            this.policies.addAll(policies);
        }
    }

    public void update(String name, Level level) {
        this.name = name;
        this.level = level;
    }
//
//    public void userAdd(User user) {
//        users.add(user);
//    }

//    public void userRemove(User user) {
//        users.remove(user);
//    }

    public void delete() {
        this.deleted = DeleteType.DELETED_YES.getValue();
    }

    @Transient
    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return authorities;
    }
}
