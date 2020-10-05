package com.farshad.product.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.farshad.product.model.Product;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryCSVImplTest {
    @Test
    public void parseLineToProduct_with6CommaSeparatedData_shouldReturnAnOptionalOfProduct(){
        //given
        ProductRepositoryCSVImpl productRepositoryCSV = new ProductRepositoryCSVImpl();
        String line = "1,P1,10,11,1,b1";
        Optional<Product> expectedProduct = Optional.of(new Product("1","P1","10","11","1","b1"));
        //when
        Optional<Product> actualProduct = productRepositoryCSV.parseLineToProduct(line);
        //then
        assertEquals(expectedProduct,actualProduct);
    }

    @Test
    public void parseLineToProduct_with5SeparatedCommaData_shouldReturnAnEmptyOptional(){
        //given
        ProductRepositoryCSVImpl productRepositoryCSV = new ProductRepositoryCSVImpl();
        String line = "1,P1,10,11,1";
        Optional<Product> expectedProduct = Optional.empty();
        //when
        Optional<Product> actualProduct = productRepositoryCSV.parseLineToProduct(line);
        //then
        assertEquals(expectedProduct,actualProduct);
    }

    @Test
    public void parseLineToProduct_withMissingAnyData_shouldReturnAnEmptyOptional(){
        //given
        ProductRepositoryCSVImpl productRepositoryCSV = new ProductRepositoryCSVImpl();
        String line = "1,P1,10,,1,b1";
        Optional<Product> expectedProduct = Optional.empty();
        //when
        Optional<Product> actualProduct = productRepositoryCSV.parseLineToProduct(line);
        //then
        assertEquals(expectedProduct,actualProduct);
    }

    @Test
    public void parseLineToProduct_withNullLine_shouldReturnAnEmptyOptional(){
        //given
        ProductRepositoryCSVImpl productRepositoryCSV = new ProductRepositoryCSVImpl();
        Optional<Product> expectedProduct = Optional.empty();
        //when
        Optional<Product> actualProduct = productRepositoryCSV.parseLineToProduct(null);
        //then
        assertEquals(expectedProduct,actualProduct);
    }


    @Test
    public void findAll_withTwoLinesData_shouldReturnListContainingThoseTwoProducts(){
        //given
        ProductRepositoryCSVImpl productRepositoryCSV = new ProductRepositoryCSVImpl();
        productRepositoryCSV = spy(productRepositoryCSV);

        Stream<String> stringStream = Stream.of("ID,NAME,PRICE,OLD_PRICE,STOCK,BRAND","1,P1,10,11,1,b1","2,P2,20,21,2,b2");
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.lines()).thenReturn(stringStream);
        Optional<BufferedReader> optionalBufferedReader = Optional.of(bufferedReader);

        doReturn(optionalBufferedReader).when(productRepositoryCSV).getReader();

        List<Product> expectedList = Arrays.asList(new Product("1","P1","10","11","1","b1"),
            new Product("2","P2","20","21","2","b2"));
        //when
        List<Product> actualList = productRepositoryCSV.findAll();
        //then
        assertEquals(expectedList,actualList);
        verify(bufferedReader).lines();
        verify(productRepositoryCSV).getReader();
    }

    @Test
    public void findAll_withNoLinesData_shouldReturnEmptyList(){
        //given
        ProductRepositoryCSVImpl productRepositoryCSV = new ProductRepositoryCSVImpl();
        productRepositoryCSV = spy(productRepositoryCSV);

        Stream<String> stringStream = Stream.of("ID,NAME,PRICE,OLD_PRICE,STOCK,BRAND");
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.lines()).thenReturn(stringStream);
        Optional<BufferedReader> optionalBufferedReader = Optional.of(bufferedReader);

        doReturn(optionalBufferedReader).when(productRepositoryCSV).getReader();

        List<Product> expectedList = Collections.emptyList();
        //when
        List<Product> actualList = productRepositoryCSV.findAll();
        //then
        assertEquals(expectedList,actualList);
        verify(bufferedReader).lines();
        verify(productRepositoryCSV).getReader();
    }

    @Test
    public void findAll_withThreeLinesDataOneOfThemMissingData_shouldReturnListContainingThoseTwoValidProducts(){
        //given
        ProductRepositoryCSVImpl productRepositoryCSV = new ProductRepositoryCSVImpl();
        productRepositoryCSV = spy(productRepositoryCSV);

        Stream<String> stringStream = Stream.of("ID,NAME,PRICE,OLD_PRICE,STOCK,BRAND","1,P1,10,11,1,b1","2,P2,20,21,,b2","3,P3,30,31,3,b3");
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.lines()).thenReturn(stringStream);
        Optional<BufferedReader> optionalBufferedReader = Optional.of(bufferedReader);

        doReturn(optionalBufferedReader).when(productRepositoryCSV).getReader();

        List<Product> expectedList = Arrays.asList(new Product("1","P1","10","11","1","b1"),
            new Product("3","P3","30","31","3","b3"));
        //when
        List<Product> actualList = productRepositoryCSV.findAll();
        //then
        assertEquals(expectedList,actualList);
        verify(bufferedReader).lines();
        verify(productRepositoryCSV).getReader();
    }

    @Test
    public void getReader_withValidPath_shouldReturnOptionalOfBufferReader(){
        //given
        ProductRepositoryCSVImpl productRepositoryCSV = new ProductRepositoryCSVImpl();
        productRepositoryCSV.setCsvFilePath("product/product_data.csv");
        //when
        Optional<BufferedReader> actualBufferedReader =  productRepositoryCSV.getReader();
        //then
        assertNotNull(actualBufferedReader);
    }

    @Test
    public void getReader_withInvalidPath_shouldReturnOptionalOfEmpty(){
        //given
        ProductRepositoryCSVImpl productRepositoryCSV = new ProductRepositoryCSVImpl();
        productRepositoryCSV.setCsvFilePath("dummy.csv");
        //when
        Optional<BufferedReader> actualBufferedReader =  productRepositoryCSV.getReader();
        //then
        assertEquals(Optional.empty(),actualBufferedReader);
    }



}