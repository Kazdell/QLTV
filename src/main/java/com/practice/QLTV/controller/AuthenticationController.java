package com.practice.QLTV.controller;

import com.nimbusds.jose.JOSEException;
import com.practice.QLTV.dto.request.AuthRequest;
import com.practice.QLTV.dto.request.IntrospectRequest;
import com.practice.QLTV.dto.request.LogoutRequest;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.dto.response.AuthResponse;
import com.practice.QLTV.dto.response.IntrospectResponse;
import com.practice.QLTV.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PreAuthorize("fileRole(#request)")
    @PostMapping("/token")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticate(HttpServletRequest request, @Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }

    @PreAuthorize("fileRole(#request)")
    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> introspect(HttpServletRequest request, @Valid @RequestBody IntrospectRequest introspectRequest)
            throws JOSEException, ParseException {
        return ResponseEntity.ok(authenticationService.introspect(introspectRequest));
    }

    @PreAuthorize("fileRole(#request)")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request, @Valid @RequestBody LogoutRequest logoutRequest)
            throws JOSEException, ParseException {
        return ResponseEntity.ok(authenticationService.logout(logoutRequest));
    }
}