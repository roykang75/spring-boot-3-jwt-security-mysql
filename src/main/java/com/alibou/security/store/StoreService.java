package com.alibou.security.store;


import com.alibou.security.advice.exception.CDuplicateRoleExistException;
import com.alibou.security.advice.exception.CDuplicateStoreExistException;
import com.alibou.security.advice.exception.CResourceNotExistException;
import com.alibou.security.common.enums.DeleteType;
import com.alibou.security.store.request.StoreRequest;
import com.alibou.security.store.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public StoreResponse save(StoreRequest storeRequest) {
        storeRepository.findByName(storeRequest.getName()).ifPresent(store -> {
            throw new CDuplicateStoreExistException();
        });

        String id = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        log.debug("## id: {}", id);

        Store store = Store.builder()
                .id(id)
                .name(storeRequest.getName())
                .email(storeRequest.getEmail())
                .telephone(storeRequest.getTelephone())
                .mobile(storeRequest.getMobile())
                .registration(storeRequest.getRegistration())
                .build();

        Store storeResp = storeRepository.save(store);
        return new StoreResponse(storeResp);
    }

    @Transactional(readOnly = true)
    public Page<StoreResponse> findAll(Pageable pageable) {
        return storeRepository.findByDeletedEquals(DeleteType.DELETED_NO.getValue(), pageable).map(StoreResponse::new);
    }

    @Transactional(readOnly = true)
    public StoreResponse find(long seq) {
        Store store = storeRepository.findByStoreSeq(seq).orElseThrow(CResourceNotExistException::new);

        return new StoreResponse(store);
    }

    @Transactional(readOnly = true)
    public Optional<Store> findById(String id) {
        Store store = storeRepository.findById(id).orElseThrow(CResourceNotExistException::new);

        return Optional.of(store);
    }

    @Transactional
    public StoreResponse update(long seq, StoreRequest storeRequest) {
        Store store = storeRepository.findByStoreSeq(seq).orElseThrow(CResourceNotExistException::new);

        store.update(storeRequest.getName(), storeRequest.getTelephone(), storeRequest.getMobile()
                , storeRequest.getEmail(), storeRequest.getRegistration());

        return new StoreResponse(store);
    }

    @Transactional
    public boolean delete(long seq) {
        Store store = storeRepository.findByStoreSeq(seq).orElseThrow(CResourceNotExistException::new);
        store.delete();

        return true;
    }


}
