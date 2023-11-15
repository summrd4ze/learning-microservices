package com.example.ShoppingService;

import com.example.ShoppingService.dto.DetailedProduct;
import com.example.ShoppingService.dto.DetailedStore;
import com.example.ShoppingService.service.ShoppingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ShoppingController {

    private final ShoppingService shoppingService;

    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @GetMapping("/products")
    public List<DetailedProduct> getAllProducts() {
        return shoppingService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public DetailedProduct getOneProduct(@PathVariable UUID id) {
        return shoppingService.getOneProducts(id);
    }

    @GetMapping("/stores")
    public List<DetailedStore> getAllStores() {
        return shoppingService.getAllStores();
    }

    @GetMapping("/stores/{id}")
    public DetailedStore getOneProduct(@PathVariable Long id) {
        return shoppingService.getOneStore(id);
    }
}
