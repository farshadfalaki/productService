package com.farshad.product.service;

import com.farshad.product.model.ProductsInformation;
import com.farshad.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;

    @Override
    @Cacheable(value = "productCache")
    public ProductsInformation fetchProductsInformation() {
        log.info("fetchProductsInformation ...");
        return new ProductsInformation(productRepository.findAll());
    }
}
