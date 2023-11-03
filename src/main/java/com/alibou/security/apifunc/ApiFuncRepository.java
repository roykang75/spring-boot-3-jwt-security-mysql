package com.alibou.security.apifunc;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiFuncRepository extends JpaRepository<ApiFunc, Integer> {

    Optional<ApiFunc> findBySeq(long seq);
    Optional<ApiFunc> findByPath(String path);
}
