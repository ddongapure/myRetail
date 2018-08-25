package com.springrestapi.repositories;

/**
 * Created by ddongapu on 8/20/2018.
 */

import com.springrestapi.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByProductId(String productId);
}

