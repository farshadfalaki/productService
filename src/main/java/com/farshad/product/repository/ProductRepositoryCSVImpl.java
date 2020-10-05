package com.farshad.product.repository;

import com.farshad.product.model.Product;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Setter
public class ProductRepositoryCSVImpl implements ProductRepository{
    @Value("${csv.file.path}")
    private String csvFilePath ;

    @Override
    public List<Product> findAll() {
        return getReader().map(BufferedReader::lines).orElse(Stream.empty())
            .skip(1)
            .map(this::parseLineToProduct)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    Optional<Product> parseLineToProduct(String line){
        if(line!=null) {
            String[] valueArr = line.split(",");
            if (valueArr.length == 6) {
                if(Stream.of(valueArr).anyMatch(String::isEmpty)) {
                    log.warn("Line skipped because of empty values : {} ", line);
                    return Optional.empty();
                }
                return Optional
                    .of(Product.builder().id(valueArr[0]).name(valueArr[1]).price(valueArr[2])
                        .oldPrice(valueArr[3]).stock(valueArr[4]).brand(valueArr[5]).build());
            } else {
                log.warn("Line skipped because of missing columns : {} ", line);
                return Optional.empty();
            }
        }else
            return Optional.empty();
    }

    Optional<BufferedReader> getReader(){
        try {
            InputStream resource = new ClassPathResource(csvFilePath).getInputStream();
            return Optional.of(new BufferedReader(new InputStreamReader(resource)));
        } catch (IOException e) {
            log.warn("Error while reading from file : ",e);
            return Optional.empty();
        }
    }
}
