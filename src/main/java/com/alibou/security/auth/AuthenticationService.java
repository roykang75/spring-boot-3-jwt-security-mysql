package com.alibou.security.auth;

import com.alibou.security.advice.exception.CUserNotFoundException;
import com.alibou.security.config.JwtService;
import com.alibou.security.operation.Operation;
import com.alibou.security.operation.OperationRepository;
import com.alibou.security.policy.Policy;
import com.alibou.security.policy.PolicyRepository;
import com.alibou.security.policy.Privilege;
import com.alibou.security.policy.PrivilegeRepository;
import com.alibou.security.role.Level;
import com.alibou.security.role.Role;
import com.alibou.security.role.RoleRepository;
import com.alibou.security.store.Store;
import com.alibou.security.store.StoreRepository;
import com.alibou.security.token.Token;
import com.alibou.security.token.TokenRepository;
import com.alibou.security.token.TokenType;
import com.alibou.security.user.User;
import com.alibou.security.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PolicyRepository policyRepository;
  private final PrivilegeRepository privilegeRepository;
  private final OperationRepository operationRepository;
  private final StoreRepository storeRepository;

  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request, Role role) {
    // store
    Store store = Store.builder()
            .id("YC3KULWG")
            .name("스타벅스")
            .telephone("02-XXXX-XXXX")
            .mobile("010-XXXX-XXXX")
            .email("starbucks@mail.com")
            .registration("111-111-00")
            .build();

    storeRepository.save(store);

    if (role == null) {
      role = Role.builder()
              .level(Level.LEVEL_RED)
              .name(request.getRole())
              .store(store)
              .build();
    }

    // -----------------
    Role newRole = Role.builder()
            .name(role.getName())
            .level(role.getLevel())
            .store(store)
            .build();
    Role storedRole = roleRepository.save(newRole);

    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(storedRole)
        .build();
    var savedUser = userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);

    Operation operation = Operation.builder()
            .name("Casting")
            .build();

    Operation storedOperation = operationRepository.save(operation);

    Privilege privilege1 = Privilege.builder()
            .name("O_RW")
            .build();

    Privilege privilege2 = Privilege.builder()
            .name("O_R")
            .build();

    Privilege privilege3 = Privilege.builder()
            .name("O_W")
            .build();

    privilegeRepository.save(privilege1);
    privilegeRepository.save(privilege2);
    privilegeRepository.save(privilege3);

    List<Privilege> privileges = new ArrayList<>();
    privileges.add(privilege1);
    privileges.add(privilege2);

    Policy policy = Policy.builder()
            .name("policy")
            .role(storedRole)
            .operation(storedOperation)
            .privileges(privileges)
            .build();

    Policy storedPolicy = policyRepository.save(policy);

    Policy readPolicy = policyRepository.findByPolicySeq(1).get();

    log.debug("##########################################################");
    log.debug("### readpolicy: {}", readPolicy.getPrivileges());

    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(CUserNotFoundException::new);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
//        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserSeq());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.userRepository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
