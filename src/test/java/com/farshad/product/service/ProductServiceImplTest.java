package com.farshad.product.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.farshad.product.model.Product;
import com.farshad.product.model.ProductsInformation;
import com.farshad.product.repository.ProductRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl productService;

    @Test
    public void fetchProductsInformation_withTwoProduct_shouldReturnAnObjectContainingThoseTwo() {
        //given
        List<Product> productList = Arrays.asList(new Product("1","P1","10","11","1","b1"),
            new Product("3","P3","30","31","3","b3"));
        when(productRepository.findAll()).thenReturn(productList);
        ProductsInformation expectedProductsInformation = new ProductsInformation(productList);
            //when
        ProductsInformation actualProductsInformation = productService.fetchProductsInformation();
        //then
        assertEquals(expectedProductsInformation,actualProductsInformation);
        verify(productRepository).findAll();
    }

    @Test
    public void fetchProductsInformation_withNoProduct_shouldReturnAnObjectContainingEmptyList() {
        //given
        List<Product> productList = Collections.emptyList();
        when(productRepository.findAll()).thenReturn(productList);
        ProductsInformation expectedProductsInformation = new ProductsInformation(productList);
        //when
        ProductsInformation actualProductsInformation = productService.fetchProductsInformation();
        //then
        assertEquals(expectedProductsInformation,actualProductsInformation);
        verify(productRepository).findAll();
    }
}