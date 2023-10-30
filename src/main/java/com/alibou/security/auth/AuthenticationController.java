package com.alibou.security.auth;

import com.alibou.security.common.service.ResponseService;
import com.alibou.security.common.service.response.SingleResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final ResponseService responseService;
  private final AuthenticationService service;

  @PostMapping("/register")
  public SingleResult<Object> register(
      @RequestBody RegisterRequest request
  ) {
    return responseService.getSingleResult(service.register(request));
  }

  @PostMapping("/authenticate")
  public SingleResult<Object> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return responseService.getSingleResult(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
