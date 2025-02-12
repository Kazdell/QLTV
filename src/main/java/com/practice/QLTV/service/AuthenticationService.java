package com.practice.QLTV.service;

import com.nimbusds.jose.JOSEException;
import com.practice.QLTV.dto.request.AuthRequest;
import com.practice.QLTV.dto.request.IntrospectRequest;
import com.practice.QLTV.dto.request.LogoutRequest;
import com.practice.QLTV.dto.response.AuthResponse;
import com.practice.QLTV.dto.response.IntrospectResponse;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface AuthenticationService {
    AuthResponse authenticate(AuthRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    void logout(LogoutRequest request) throws ParseException, JOSEException;
}
