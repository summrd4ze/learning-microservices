package com.example.ShoppingService.dto;

import java.util.List;

public record DetailedStore(Long id, String name, List<DetailedProduct> products){
}
