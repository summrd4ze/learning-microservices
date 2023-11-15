package com.example.ShoppingService.dto;

import java.util.List;
import java.util.UUID;

public record Store(Long id, String name, List<SimpleProduct> products) {
    public List<UUID> productUUIDs() {
        return products.stream().map(SimpleProduct::id).toList();
    }
}
