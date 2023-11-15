package com.example.ShoppingService.service;

import com.example.ShoppingService.config.ProductServiceConfig;
import com.example.ShoppingService.config.StoreServiceConfig;
import com.example.ShoppingService.dto.DetailedProduct;
import com.example.ShoppingService.dto.DetailedStore;
import com.example.ShoppingService.dto.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingService {

    private static final Logger log = LoggerFactory.getLogger(ShoppingService.class);

    private final RestTemplate restTemplate;
    private final ProductServiceConfig productServiceConfig;

    private final StoreServiceConfig storeServiceConfig;

    public ShoppingService(
            RestTemplate restTemplate,
            ProductServiceConfig productServiceConfig,
            StoreServiceConfig storeServiceConfig
    ) {
        this.restTemplate = restTemplate;
        this.productServiceConfig = productServiceConfig;
        this.storeServiceConfig = storeServiceConfig;
    }

    public List<DetailedProduct> getAllProducts() {
        URI getAllProductsURI = URI.create(productServiceConfig.host() + productServiceConfig.getAllEndpoint());
        log.info("Calling {}", getAllProductsURI);
        var res = restTemplate.exchange(getAllProductsURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<DetailedProduct>>() {});
        return res.getBody();
    }

    @NewSpan("my-span")
    private List<DetailedProduct> getAllProducts(List<UUID> products) {
        StringBuilder queryBuilder = new StringBuilder("?uuids=");
        for (int i = 0; i < products.size(); i++) {
            queryBuilder.append(URLEncoder.encode(products.get(i).toString(), StandardCharsets.UTF_8));
            if (i < products.size() - 1) {
                queryBuilder.append(",");
            }
        }
        URI getAllProductsURI = URI.create(productServiceConfig.host() + productServiceConfig.getBatchEndpoint() + queryBuilder);
        log.info("Calling {}", getAllProductsURI);
        var res = restTemplate.exchange(getAllProductsURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<DetailedProduct>>() {});
        return res.getBody();
    }

    public DetailedProduct getOneProducts(UUID uuid) {
        URI getOneProductURI = URI.create(String.format(productServiceConfig.host() + productServiceConfig.getOneEndpoint(), uuid));
        log.info("Calling {}", getOneProductURI);
        return restTemplate.getForObject(getOneProductURI, DetailedProduct.class);
    }

    public List<DetailedStore> getAllStores() {
        URI getAllStoresURI = URI.create(storeServiceConfig.host() + storeServiceConfig.getAllEndpoint());
        log.info("Calling {}", getAllStoresURI);
        var stores = restTemplate.exchange(getAllStoresURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>() {}).getBody();
        if (stores != null) {
            return stores
                    .stream()
                    .map(store -> {
                        List<DetailedProduct> detailedProducts = this.getAllProducts(store.productUUIDs());
                        return new DetailedStore(store.id(), store.name(), detailedProducts);
                    })
                    .toList();
        }
        return List.of();
    }

    public DetailedStore getOneStore(Long id) {
        URI getOneStoreURI = URI.create(String.format(storeServiceConfig.host() + storeServiceConfig.getOneEndpoint(), id));
        log.info("Calling {}", getOneStoreURI);
        Store store = restTemplate.getForObject(getOneStoreURI, Store.class);
        if (store != null) {
            List<DetailedProduct> detailedProducts = this.getAllProducts(store.productUUIDs());
            return new DetailedStore(store.id(), store.name(), detailedProducts);
        }
        return null;
    }
}
