package com.digitalstore.service;

import java.util.List;

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
    public Product findByProductId(String productId) {
        return productRepo.findById(productId).get();
    }
    public List<Product> findBySellerId(String sellerId) {
        return productRepo.findBySellerId(sellerId);
    }
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }
    public List<Product> findByProductName(String productName) {
        return productRepo.findByProductName(productName);
    }
    public boolean isProductExist(String productId){
        return productRepo.existsById(productId);
    }
}
