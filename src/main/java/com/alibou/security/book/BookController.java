package com.alibou.security.book;

import com.alibou.security.common.service.ResponseService;
import com.alibou.security.common.service.response.ListResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final ResponseService responseService;
    private final BookService service;

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody BookRequest request
    ) {
        service.save(request);
        return ResponseEntity.accepted().build();
    }

//    @GetMapping
//    public ResponseEntity<List<Book>> findAllBooks() {
//        return ResponseEntity.ok(service.findAll());
//    }

    @GetMapping
    public ListResult<Book> findAllBooks() {
        return responseService.getListResult(service.findAll());
    }
}
