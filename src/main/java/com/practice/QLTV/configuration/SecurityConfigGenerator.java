package com.practice.QLTV.configuration;

import com.practice.QLTV.entity.Function;
import com.practice.QLTV.repository.FunctionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Configuration
public class SecurityConfigGenerator {

    private static final String PROPERTIES_FILE = "D:\\Work\\Task\\QLTV-master\\QLTV\\src\\main\\resources\\role.properties";

    @Bean
    public Map<String, String> generateSecurityMappings(RequestMappingHandlerMapping mappingHandler, FunctionRepository functionRepository) {
        Map<String, String> securityMappings = new HashMap<>();

        mappingHandler.getHandlerMethods().forEach((requestMapping, handlerMethod) -> {
            requestMapping.getMethodsCondition().getMethods().forEach(httpMethod -> {
                String apiPath = "/" + String.join("/", requestMapping.getPatternValues());
                apiPath = apiPath.replaceAll("//", "/");

                String permissionKey = generatePermissionKey(apiPath);

                // Save only if not exists
                if (!functionRepository.existsByFunctionCode(permissionKey)) {
                    Function function = new Function();
                    function.setFunctionCode(permissionKey);
                    function.setFunctionName( apiPath);
                    function.setDescription("Auto-generated function permission");
                    functionRepository.save(function);
                }

                securityMappings.put(apiPath, permissionKey);
            });
        });

        // Write to role.properties
        writePropertiesFile(securityMappings);

        return securityMappings;
    }

    private String generatePermissionKey(String apiPath) {
        return apiPath.replaceAll("[{}]", "")
                .replace("/", "_")
                .toUpperCase()
                .replaceAll("^_", "");
    }

    private void writePropertiesFile(Map<String, String> securityMappings) {
        Properties properties = new Properties();

        securityMappings.forEach(properties::setProperty);

        try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(out, "Generated Role Mappings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
