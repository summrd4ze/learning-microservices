package com.example.StoreService;

import com.example.StoreService.model.Store;
import com.example.StoreService.repository.StoreRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {

    private final StoreRepository storeRepository;

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping("/stores")
    public List<Store> getStores() {
        return storeRepository.findAll();
    }

    @GetMapping("/stores/{id}")
    public Store getStore(@PathVariable Long id) {
        return storeRepository.findById(id).orElse(null);
    }
}
