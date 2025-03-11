package com.practice.QLTV.configuration;

import com.practice.QLTV.configuration.CustomMethodSecurityExpressionRoot;
import com.practice.QLTV.configuration.CustomPermissionEvaluator;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.Properties;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
    private final Properties properties;

    public CustomMethodSecurityExpressionHandler(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        CustomMethodSecurityExpressionRoot root =
                new CustomMethodSecurityExpressionRoot(authentication);
        root.setPermissionEvaluator(new CustomPermissionEvaluator(properties));
        return root;
    }
}
