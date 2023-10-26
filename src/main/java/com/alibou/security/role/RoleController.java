package com.alibou.security.role;

import com.alibou.security.common.service.ResponseService;
import com.alibou.security.common.service.response.SingleResult;
import com.alibou.security.role.request.RoleRequest;
import com.alibou.security.role.response.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final ResponseService responseService;
    private final RoleService roleService;

    @PostMapping
    public SingleResult<Object> create(@RequestBody RoleRequest roleRequest) {
        return responseService.getSingleResult(roleService.save(roleRequest));
    }

    @GetMapping("/{seq}")
    public SingleResult<Object> read(@PathVariable("seq") long seq) {
        return responseService.getSingleResult(roleService.find(seq));
    }


}
