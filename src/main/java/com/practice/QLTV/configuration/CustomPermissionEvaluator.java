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
        return hasRole(authentication, uri.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String uri, Object method) {
        if (authentication == null || uri == null || method == null) {
            return false;
        }
        return hasRole(authentication, uri);
    }

    private boolean hasRole(Authentication authentication, String targetDomainObject) {
        String functionCode = convertString(targetDomainObject); // Convert to function code format

        for (Object key : properties.keySet()) {
            if (functionCode.equalsIgnoreCase(key.toString()) &&
                    authentication.getAuthorities().contains(new SimpleGrantedAuthority(properties.getProperty((String) key)))) {
                return true;
            }
        }

        return false;
    }

    private String convertString(String target) {
        return target.replace("/", "_").toUpperCase();
    }
}
