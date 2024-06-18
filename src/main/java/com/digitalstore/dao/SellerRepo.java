package com.digitalstore.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.digitalstore.model.sellers.Seller;
import java.util.List;


@Repository
public interface SellerRepo extends MongoRepository<Seller, String> {

    List<Seller> findBySellerEmail(String sellerEmail);
    List<Seller> findBySellerName(String sellerName);
    List<Seller> findBySellerId(String sellerId);
    
}