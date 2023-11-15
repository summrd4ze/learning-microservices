package com.example.ShoppingService.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "product.service")
public record ProductServiceConfig(String host, String getAllEndpoint, String getBatchEndpoint, String getOneEndpoint) {
}
