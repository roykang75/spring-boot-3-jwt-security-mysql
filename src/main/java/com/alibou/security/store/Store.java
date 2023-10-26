package com.alibou.security.store;

import com.alibou.security.common.entity.CommonDateEntity;
import com.alibou.security.common.enums.DeleteType;
import com.alibou.security.role.Level;
import com.alibou.security.role.Role;
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
public class Store extends CommonDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeSeq;

    @Column(nullable = false, length = 10)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(nullable = false, length = 14)
    private String mobile;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "registration", nullable = true, length = 10)
    private String registration;

    @Column(columnDefinition="tinyint(1) default 0")
    private int deleted;

    @OneToMany(mappedBy = "store")
    private List<Role> roles = new ArrayList<>();

    public void update(String name, String telephone, String mobile, String email, String registration) {
        this.name = name;
        this.telephone = telephone;
        this.mobile = mobile;
        this.email = email;
        this.registration = registration;
    }

    public void delete() {
        this.deleted = DeleteType.DELETED_YES.getValue();
    }
}
