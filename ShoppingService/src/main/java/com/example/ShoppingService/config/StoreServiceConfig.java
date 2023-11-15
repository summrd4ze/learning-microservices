package com.example.ShoppingService.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "store.service")
public record StoreServiceConfig(String host, String getAllEndpoint, String getOneEndpoint) {
}
