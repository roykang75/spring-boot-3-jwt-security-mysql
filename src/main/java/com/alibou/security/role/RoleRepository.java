package com.alibou.security.role;

import com.alibou.security.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    Optional<Role> findByName(String name);
    Optional<Role> findByNameAndStore(String name, Store store);
    Optional<Role> findBySeq(long seq);

    Page<Role> findByDeletedEquals(int deleted, Pageable pageable);
}
