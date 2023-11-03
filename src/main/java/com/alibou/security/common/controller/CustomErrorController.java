package com.alibou.security.common.controller;

import com.alibou.security.advice.exception.ForbiddenException;
import com.alibou.security.advice.exception.InternalServerException;
import com.alibou.security.advice.exception.ResourceNotExistException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping(value = "/error")
    public ResponseEntity<Object> handleNoHandlerFoundException(HttpServletResponse response, HttpServletRequest request) {
        log.debug("## handleNoHandlerFoundException");
        int status = response.getStatus();
        log.debug("## status: {}", status);

        switch (status) {
            case 403 -> throw new ForbiddenException();
            case 404 -> throw new ResourceNotExistException();
            case 500 -> throw new InternalServerException();
        }

        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }
}
