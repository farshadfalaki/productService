package com.farshad.product.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private String id;
    private String name;
    private String price;
    private String oldPrice;
    private String stock;
    private String brand;
}
