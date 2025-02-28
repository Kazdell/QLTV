package com.practice.QLTV.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.practice.QLTV.dto.request.AuthRequest;
import com.practice.QLTV.dto.request.IntrospectRequest;
import com.practice.QLTV.dto.request.LogoutRequest;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.dto.response.AuthResponse;
import com.practice.QLTV.dto.response.IntrospectResponse;
import com.practice.QLTV.entity.InvalidatedToken;
import com.practice.QLTV.entity.User;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.repository.InvalidateRepository;
import com.practice.QLTV.repository.UserRepository;
import com.practice.QLTV.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {
    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    final UserRepository userRepository;
    final InvalidateRepository invalidateRepository;
    final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<AuthResponse> authenticate(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateToken(user);
        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
        return ApiResponse.<AuthResponse>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) // 1000
                .message("Authentication successful")
                .result(authResponse)
                .build();
    }

    @Override
    public ApiResponse<IntrospectResponse> introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isValid;
        try {
            verifyToken(token);
            isValid = true;
        } catch (AppException e) {
            isValid = false;
        }
        IntrospectResponse introspectResponse = IntrospectResponse.builder()
                .valid(isValid)
                .build();
        return ApiResponse.<IntrospectResponse>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) // 1000
                .message("Token introspection completed")
                .result(introspectResponse)
                .build();
    }

    @Override
    public ApiResponse<Void> logout(LogoutRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken());
        String jid = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jid)
                .expiryTime(new java.sql.Date(expiryTime.getTime()))
                .build();
        invalidateRepository.save(invalidatedToken);
        return ApiResponse.<Void>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) // 1000
                .message("Logout successful")
                .result(null)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("duyanh.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", user.getRoleGroupId())
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION, "Token generation failed");
        }
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        if (!verified || expiryTime.before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (invalidateRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }
}