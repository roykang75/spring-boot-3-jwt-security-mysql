package com.alibou.security.advice;

import com.alibou.security.advice.exception.*;
import com.alibou.security.common.service.ResponseService;
import com.alibou.security.common.service.response.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(CUserExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult duplicateUserException(HttpServletRequest request, CUserExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("userExisted.code")), getMessage("userExisted.msg"));
    }

    @ExceptionHandler(CEmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    @ExceptionHandler(CResourceNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult resourceNotExistException(HttpServletRequest request, CResourceNotExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("resourceNotExist.code")), getMessage("resourceNotExist.msg"));
    }

    @ExceptionHandler(CResourceSaveFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult resourceSaveFailException(HttpServletRequest request, CResourceSaveFailException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("resourceSaveFail.code")), getMessage("resourceSaveFail.msg"));
    }

    @ExceptionHandler(CDuplicateResourceExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResult duplicateResourceExistException(HttpServletRequest request, CDuplicateResourceExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("duplicateResourceExist.code")), getMessage("duplicateResourceExist.msg"));
    }

    @ExceptionHandler(CDuplicateRoleExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResult duplicateRoleExistException(HttpServletRequest request, CDuplicateRoleExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("duplicateRoleExist.code")), getMessage("duplicateRoleExist.msg"));
    }

    @ExceptionHandler(CDuplicatePolicyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResult duplicatePolicyExistException(HttpServletRequest request, CDuplicatePolicyExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("duplicatePolicyExist.code")), getMessage("duplicatePolicyExist.msg"));
    }

    @ExceptionHandler(CDuplicateStoreExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResult duplicateStoreExistException(HttpServletRequest request, CDuplicateStoreExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("duplicateStoreExist.code")), getMessage("duplicateStoreExist.msg"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("methodArgumentNotValid.code")), getMessage("methodArgumentNotValid.msg"));
    }

    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }
    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
