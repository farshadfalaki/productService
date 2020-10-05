package com.farshad.product.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.farshad.product.model.Product;
import com.farshad.product.model.ProductsInformation;
import com.farshad.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    ProductService productService;

    @Test
    public void getProducts_withTwoProduct_shouldReturnJsonContaingThoseTwo() throws JsonProcessingException {
        //given
        ProductServiceController productServiceController = new ProductServiceController(productService,objectMapper);

        ProductsInformation productsInformation = new ProductsInformation(
            Arrays.asList(
                new Product("1","P1","10","11","1","b1"),
                new Product("1","P1","10","11","1","b1")
            )
        );
        when(productService.fetchProductsInformation()).thenReturn(productsInformation);
        //when
        String actualResult = productServiceController.getProducts();
        //then
        assertEquals(productsInformation,objectMapper.readValue(actualResult,ProductsInformation.class));
    }

    @Test
    public void getProducts_withNoProduct_shouldReturnJsonContainingThoseTwo() throws JsonProcessingException {
        //given
        ProductServiceController productServiceController = new ProductServiceController(productService,objectMapper);

        ProductsInformation productsInformation = new ProductsInformation(Collections.emptyList());
        when(productService.fetchProductsInformation()).thenReturn(productsInformation);
        //when
        String actualResult = productServiceController.getProducts();
        System.out.println(actualResult);
        //then
        assertEquals(productsInformation,objectMapper.readValue(actualResult,ProductsInformation.class));
    }
}