package com.example.StoreService;

import com.example.StoreService.model.Product;
import com.example.StoreService.model.Store;
import com.example.StoreService.repository.StoreRepository;
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
    CommandLineRunner initDatabase(StoreRepository repository) {

        Product apples = new Product(UUID.fromString("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000"));
        Product bread = new Product(UUID.fromString("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc001"));
        Product chocolate = new Product(UUID.fromString("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc002"));
        Product toothpaste = new Product(UUID.fromString("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc003"));

        Store store1 = new Store("Mega Image");
        Store store2 = new Store("Carrefour");
        store1.addProduct(apples);
        store1.addProduct(toothpaste);
        store2.addProduct(bread);
        store2.addProduct(chocolate);

        return args -> {
            if (repository.count() == 0) {
                log.info("Preloading " + repository.save(store1));
                log.info("Preloading " + repository.save(store2));
            }
        };
    }
}
