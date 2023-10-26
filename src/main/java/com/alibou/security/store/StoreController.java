package com.alibou.security.store;

import com.alibou.security.common.service.ResponseService;
import com.alibou.security.common.service.response.SingleResult;
import com.alibou.security.store.request.StoreRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class StoreController {

    private final ResponseService responseService;
    private final StoreService storeService;

    @PostMapping
    public SingleResult<Object> create(@RequestBody StoreRequest storeRequest) {
        return responseService.getSingleResult(storeService.save(storeRequest));
    }

    @GetMapping("/{seq}")
    public SingleResult<Object> read(@PathVariable("seq") long seq) {
        return responseService.getSingleResult(storeService.find(seq));
    }

}
