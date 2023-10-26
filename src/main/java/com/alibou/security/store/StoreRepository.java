package com.alibou.security.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer>, JpaSpecificationExecutor<Store> {
    Optional<Store> findByName(String name);
    Optional<Store> findByStoreSeq(long seq);

    Optional<Store> findById(String id);

    Page<Store> findByDeletedEquals(int deleted, Pageable pageable);
}
