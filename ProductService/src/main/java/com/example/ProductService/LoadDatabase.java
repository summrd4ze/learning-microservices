package com.example.ProductService;

import com.example.ProductService.model.Product;
import com.example.ProductService.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {

        return args -> {
            if (repository.count() == 0) {
                log.info("Preloading " + repository.save(new Product(UUID.fromString("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000"), "apples", "fruits", 10.5)));
                log.info("Preloading " + repository.save(new Product(UUID.fromString("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc001"), "bread", "bakery", 3.45)));
                log.info("Preloading " + repository.save(new Product(UUID.fromString("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc002"), "chocolate", "sweets", 7.15)));
                log.info("Preloading " + repository.save(new Product(UUID.fromString("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc003"), "toothpaste", "cosmetics", 2.2)));
            }
        };
    }
}
