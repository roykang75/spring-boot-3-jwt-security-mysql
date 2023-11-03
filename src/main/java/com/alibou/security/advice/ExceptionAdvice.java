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

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult duplicateUserException(HttpServletRequest request, UserExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("userExisted.code")), getMessage("userExisted.msg"));
    }

    @ExceptionHandler(EmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, EmailSigninFailedException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    @ExceptionHandler(AuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, AuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    @ExceptionHandler(ResourceNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult resourceNotExistException(HttpServletRequest request, ResourceNotExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("resourceNotExist.code")), getMessage("resourceNotExist.msg"));
    }

    @ExceptionHandler(ResourceSaveFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult resourceSaveFailException(HttpServletRequest request, ResourceSaveFailException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("resourceSaveFail.code")), getMessage("resourceSaveFail.msg"));
    }

    @ExceptionHandler(DuplicateResourceExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResult duplicateResourceExistException(HttpServletRequest request, DuplicateResourceExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("duplicateResourceExist.code")), getMessage("duplicateResourceExist.msg"));
    }

    @ExceptionHandler(DuplicateRoleExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResult duplicateRoleExistException(HttpServletRequest request, DuplicateRoleExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("duplicateRoleExist.code")), getMessage("duplicateRoleExist.msg"));
    }

    @ExceptionHandler(DuplicatePolicyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResult duplicatePolicyExistException(HttpServletRequest request, DuplicatePolicyExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("duplicatePolicyExist.code")), getMessage("duplicatePolicyExist.msg"));
    }

    @ExceptionHandler(DuplicateStoreExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResult duplicateStoreExistException(HttpServletRequest request, DuplicateStoreExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("duplicateStoreExist.code")), getMessage("duplicateStoreExist.msg"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("methodArgumentNotValid.code")), getMessage("methodArgumentNotValid.msg"));
    }

    @ExceptionHandler(JwtNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult jwtNotValidException(HttpServletRequest request, JwtNotValidException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("jwtNotValidException.code")), getMessage("jwtNotValidException.msg"));
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult forbiddenException(HttpServletRequest request, ForbiddenException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("forbiddenException.code")), getMessage("forbiddenException.msg"));
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult internalServerException(HttpServletRequest request, InternalServerException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("internalServerException.code")), getMessage("internalServerException.msg"));
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
