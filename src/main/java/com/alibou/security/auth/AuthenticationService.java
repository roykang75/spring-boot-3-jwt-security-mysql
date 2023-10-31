package com.alibou.security.auth;

import com.alibou.security.advice.exception.UserNotFoundException;
import com.alibou.security.config.JwtService;
import com.alibou.security.operation.Operation;
import com.alibou.security.operation.OperationRepository;
import com.alibou.security.policy.Policy;
import com.alibou.security.policy.PolicyRepository;
import com.alibou.security.policy.Privilege;
import com.alibou.security.policy.PrivilegeRepository;
import com.alibou.security.role.Role;
import com.alibou.security.role.RoleRepository;
import com.alibou.security.store.Store;
import com.alibou.security.store.StoreRepository;
import com.alibou.security.token.Token;
import com.alibou.security.token.TokenRepository;
import com.alibou.security.token.TokenType;
import com.alibou.security.user.Address;
import com.alibou.security.user.User;
import com.alibou.security.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // store
        Store store = Store.builder()
                .name("스타벅스")
                .telephone("02-XXXX-XXXX")
                .mobile("010-XXXX-XXXX")
                .email("starbucks@mail.com")
                .registration("111-111-00")
                .build();

        Store storedStore = storeRepository.save(store);

        Policy policy = makeTestPolicy();
        List<Policy> policies = new ArrayList<>();
        policies.add(policy);

        // -----------------
        Role role = Role.builder()
                .level("RED")
                .name(request.getRole())
                .store(storedStore)
                .policies(policies)
                .build();
        Role storedRole = roleRepository.save(role);

        // ----------------
        Address address = Address.builder()
                .zipCode("11-111")
                .address1("address1")
                .address2("address2")
                .build();

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(storedRole)
                .address(address)
                .build();

//        storedRole.userAdd(user);

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        log.debug("### user: {}", user.toString());

        Operation operation = Operation.builder()
                .name("Casting")
                .policies(policies)
                .build();

        operationRepository.save(operation);
        Policy readPolicy = policyRepository.findByPolicySeq(1).get();
        Operation readOperation = operationRepository.findByOperationSeq(1).get();
        Role readRole = roleRepository.findByRoleSeq(1).get();
        User readUser = userRepository.findByEmail(request.getEmail()).get();

        log.debug("##########################################################");
        log.debug("### policy: {}", readPolicy.toString());
        log.debug("### operation: {}", readOperation.toString());
        log.debug("### role: {}", readRole.toString());
        log.debug("### user: {}", readUser.toString());

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Policy makeTestPolicy() {
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
                .privileges(privileges)
                .build();

        Policy storedPolicy = policyRepository.save(policy);

        return storedPolicy;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
//        .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserSeq());
        if (validUserTokens.isEmpty()) {
            return;
        }
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
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow(UserNotFoundException::new);
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
