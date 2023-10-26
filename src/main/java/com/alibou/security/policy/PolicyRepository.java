package com.alibou.security.policy;

import com.alibou.security.policy.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {
    Optional<Policy> findByPolicySeq(long seq);
}
