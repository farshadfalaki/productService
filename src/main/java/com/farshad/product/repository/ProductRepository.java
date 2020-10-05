package com.farshad.product.repository;

import com.farshad.product.model.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}
