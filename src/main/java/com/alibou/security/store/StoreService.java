package com.alibou.security.store;


import com.alibou.security.advice.exception.DuplicateStoreExistException;
import com.alibou.security.advice.exception.ResourceNotExistException;
import com.alibou.security.common.enums.DeleteType;
import com.alibou.security.store.request.StoreRequest;
import com.alibou.security.store.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            throw new DuplicateStoreExistException();
        });

        Store store = Store.builder()
                .name(storeRequest.getName())
                .telephone(storeRequest.getTelephone())
                .mobile(storeRequest.getMobile())
                .email(storeRequest.getEmail())
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
        Store store = storeRepository.findByStoreSeq(seq).orElseThrow(ResourceNotExistException::new);

        return new StoreResponse(store);
    }

    @Transactional(readOnly = true)
    public Optional<Store> findById(String id) {
        Store store = storeRepository.findById(id).orElseThrow(ResourceNotExistException::new);

        return Optional.of(store);
    }

    @Transactional
    public StoreResponse update(long seq, StoreRequest storeRequest) {
        Store store = storeRepository.findByStoreSeq(seq).orElseThrow(ResourceNotExistException::new);

        store.update(storeRequest.getName(), storeRequest.getTelephone(), storeRequest.getMobile()
                , storeRequest.getEmail(), storeRequest.getRegistration());

        return new StoreResponse(store);
    }

    @Transactional
    public boolean delete(long seq) {
        Store store = storeRepository.findByStoreSeq(seq).orElseThrow(ResourceNotExistException::new);
        store.delete();

        return true;
    }


}
