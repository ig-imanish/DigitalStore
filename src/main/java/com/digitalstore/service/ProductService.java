package com.digitalstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalstore.dao.ProductRepo;
import com.digitalstore.model.Product;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Product saveProduct(Product product){
        return productRepo.save(product);
    }

    public void removeProduct(String productId){
        productRepo.deleteById(productId);
    }

    public boolean isProductExist(String productId){
        return productRepo.existsById(productId);
    }
}
