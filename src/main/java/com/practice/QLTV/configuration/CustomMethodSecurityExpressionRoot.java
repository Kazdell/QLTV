package com.practice.QLTV.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean fileRole(HttpServletRequest request) {
        return hasPermission(request.getRequestURI(), request.getMethod());
    }

    @Override
    public void setFilterObject(Object filterObject) {}

    @Override
    public Object getFilterObject() { return null; }

    @Override
    public void setReturnObject(Object returnObject) {}

    @Override
    public Object getReturnObject() { return null; }

    @Override
    public Object getThis() { return null; }
}
