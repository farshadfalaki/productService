package com.farshad.product.controller;

import com.farshad.product.model.ProductsInformation;
import com.farshad.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductServiceController {
    private ProductService productService;
    private ObjectMapper objectMapper;

    @GetMapping(value = "/product")
    public String getProducts() throws JsonProcessingException {
        ProductsInformation productsInformation = productService.fetchProductsInformation();
        return objectMapper.writeValueAsString(productsInformation);
    }

    @GetMapping(value = "/evictCache")
    @CacheEvict(value = "productCache")
    public void evictCache() {
        log.info("Evicting product cache");
    }
}
