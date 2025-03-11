package com.practice.QLTV.configuration;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Properties;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final Properties properties;

    public CustomPermissionEvaluator(Properties properties) {
        this.properties = properties;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object uri, Object method) {
        if (authentication == null || uri == null || method == null) {
            return false;
        }
        return hasRole(authentication, uri.toString(), method.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String uri, Object method) {
        if (authentication == null || uri == null || method == null) {
            return false;
        }
        return hasRole(authentication, uri, method.toString());
    }

    private boolean hasRole(Authentication authentication, String uri, String method) {
        String functionCode = properties.getProperty(uri);
        if (functionCode == null) {
            return false;
        }

        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(functionCode));
    }
}