package com.alibou.security.operation;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {

    Optional<Operation> findByOperationSeq(long seq);
}
