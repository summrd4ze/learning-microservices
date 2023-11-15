package com.example.ShoppingService.dto;

import java.util.UUID;

public record DetailedProduct(UUID id, String name, String description, double price) {
}
