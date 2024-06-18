package com.digitalstore.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.digitalstore.model.Product;
import java.util.List;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
    List<Product> findByProductId(String productId);
    List<Product> findByProductName(String productName);
    List<Product> findByProductPrice(float productPrice);
    List<Product> findBySellerId(String sellerId);
}
