package com.example.ProductService;

import com.example.ProductService.model.Product;
import com.example.ProductService.repository.ProductRepository;
import org.springframework.cloud.sleuth.SpanCustomizer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductsController {

    private final ProductRepository productRepository;

    private final SpanCustomizer spanCustomizer;

    public ProductsController(ProductRepository productRepository, SpanCustomizer spanCustomizer) {
        this.productRepository = productRepository;
        this.spanCustomizer = spanCustomizer;
    }

    @GetMapping(value = "/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping(value = "/products/{productId}")
    public Product getProduct(@PathVariable UUID productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @GetMapping(value = "/products/batch")
    public List<Product> getProductsForUUIDs(@RequestParam List<UUID> uuids) {
        spanCustomizer.tag("batch", uuids.toString());
        return productRepository.findAllById(uuids);
    }
}
