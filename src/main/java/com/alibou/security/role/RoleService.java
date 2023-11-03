package com.alibou.security.role;

import com.alibou.security.advice.exception.DuplicateRoleExistException;
import com.alibou.security.advice.exception.ResourceNotExistException;
import com.alibou.security.common.enums.DeleteType;
import com.alibou.security.role.request.RoleRequest;
import com.alibou.security.role.response.RoleResponse;
import com.alibou.security.store.Store;
import com.alibou.security.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final StoreService storeService;

    @Transactional
    public RoleResponse save(RoleRequest roleRequest) {
        Store store = storeService.findById(roleRequest.getStoreId()).orElseThrow(ResourceNotExistException::new);
//        roleRepository.findByName(roleRequest.getName()).ifPresent(role -> {
//            throw new CDuplicateRoleExistException();
//        });
        roleRepository.findByNameAndStore(roleRequest.getName(), store).ifPresent(role -> {
            throw new DuplicateRoleExistException();
        });

        Level level = Level.getLevel(roleRequest.getLevel());
        Role role = Role.builder()
                .name(roleRequest.getName())
                .level("RED")
                .store(store)
                .build();

        Role roleResp = roleRepository.save(role);
        return new RoleResponse(roleResp);
    }

    @Transactional(readOnly = true)
    public Page<RoleResponse> findAll(Pageable pageable) {
        return roleRepository.findByDeletedEquals(DeleteType.DELETED_NO.getValue(), pageable).map(RoleResponse::new);
    }

    @Transactional(readOnly = true)
    public RoleResponse find(long seq) {
        Role role = roleRepository.findBySeq(seq).orElseThrow(ResourceNotExistException::new);

        return new RoleResponse(role);
    }

    @Transactional
    public RoleResponse update(long seq, RoleRequest roleRequest) {
        Role role = roleRepository.findBySeq(seq).orElseThrow(ResourceNotExistException::new);
        Level level = Level.getLevel(roleRequest.getLevel());
        role.update(roleRequest.getName(), level);

        return new RoleResponse(role);
    }

    @Transactional
    public boolean delete(long seq) {
        Role role = roleRepository.findBySeq(seq).orElseThrow(ResourceNotExistException::new);
        role.delete();

        return true;
    }

}
