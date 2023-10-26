package com.alibou.security.operation;

import com.alibou.security.operation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
}
