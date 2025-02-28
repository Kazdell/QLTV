package com.practice.QLTV.configuration;

import com.practice.QLTV.entity.Function;
import com.practice.QLTV.repository.FunctionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfigGenerator {

    private static final String PROPERTIES_FILE = "D:\\Work\\Task\\QLTV-master\\QLTV\\src\\main\\resources\\role.properties";
    private static final Logger log = LoggerFactory.getLogger(SecurityConfigGenerator.class);

    @Bean
    public Map<String, String> generateSecurityMappings(RequestMappingHandlerMapping mappingHandler, FunctionRepository functionRepository) {
        Map<String, String> securityMappings = new HashMap<>();

        mappingHandler.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            // Get the controller-level base path for prefix generation
            final String basePath = extractBasePath(handlerMethod.getBeanType()) != null
                    ? extractBasePath(handlerMethod.getBeanType())
                    : "";
            log.debug("Processing controller: {}, basePath: {}", handlerMethod.getBeanType().getSimpleName(), basePath);
            Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
            requestMappingInfo.getPatternValues().forEach(pattern -> {
                final String fullPath = pattern.replaceAll("//", "/");
                log.debug("Full Path from pattern (cleaned): {}", fullPath);
                methods.forEach(httpMethod -> {
                    log.debug("Full Path: {}, HTTP Method: {}", fullPath, httpMethod);
                    String functionCode = generateFunctionCode(fullPath, httpMethod);
                    String description = generateDescription(fullPath, httpMethod);

                    // Check duplicate
                    if (!functionRepository.existsByFunctionCode(functionCode)) {
                        Function function = Function.builder()
                                .functionCode(functionCode)
                                .functionName(fullPath)
                                .description(description)
                                .build();
                        functionRepository.save(function);
                        log.info("Saved Function: {} -> {}", fullPath, functionCode);
                    }

                    securityMappings.put(fullPath, functionCode);
                });
            });
        });

        // Write to role.properties
        writePropertiesFile(securityMappings);

        return securityMappings;
    }

    private String extractBasePath(Class<?> controllerClass) {
        if (controllerClass.isAnnotationPresent(org.springframework.web.bind.annotation.RequestMapping.class)) {
            org.springframework.web.bind.annotation.RequestMapping mapping =
                    controllerClass.getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class);
            String[] paths = mapping.value();
            if (paths.length > 0) {
                return paths[0];
            }
        }
        return "";
    }

    private String generateFunctionCode(String fullPath, RequestMethod httpMethod) {
        String[] segments = fullPath.split("/");
        String basePrefix;
        String pathPart;
        // Handle base prefix
        if (segments.length > 2) {
            basePrefix = Arrays.stream(segments, 1, Math.min(3, segments.length))
                    .filter(part -> !part.isEmpty())
                    .map(String::toUpperCase)
                    .collect(Collectors.joining("_"));
        } else {
            basePrefix = "API";
        }

        // Handle specific path, removing dynamic parts like {id}
        if (segments.length > 3) {
            pathPart = Arrays.stream(segments, 3, segments.length)
                    .filter(part -> !part.contains("{") && !part.contains("}"))
                    .map(String::toUpperCase)
                    .collect(Collectors.joining("_"));
        } else if (segments.length == 3 && !segments[2].contains("{") && !segments[2].contains("}")) {
            pathPart = segments[2].toUpperCase();
        } else {
            pathPart = "INDEX";
        }

        // Combine with HTTP method (e.g., "GET")
        String methodPart = httpMethod.name().toUpperCase();

        if (basePrefix.isEmpty()) {
            basePrefix = "API";
        }
        if (pathPart.isEmpty()) {
            pathPart = "INDEX";
        }

        return String.format("%s_%s_%s", basePrefix, pathPart, methodPart);
    }

    private String generateDescription(String fullPath, RequestMethod httpMethod) {
        return String.format("Handles %s requests to %s", httpMethod.name(), fullPath);
    }

    private void writePropertiesFile(Map<String, String> securityMappings) {
        Properties properties = new Properties();
        securityMappings.forEach(properties::setProperty);

        try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(out, "Generated Role Mappings");
        } catch (IOException e) {
            log.error("Failed to write properties file: {}", e.getMessage(), e);
        }
    }
}