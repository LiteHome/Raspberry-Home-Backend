package com.rashome.rashome.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "nextcloud") 
@ConfigurationPropertiesScan
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NextcloudConfig {
    
    private String username;
    private String password;
    private String baseUrl;
}
