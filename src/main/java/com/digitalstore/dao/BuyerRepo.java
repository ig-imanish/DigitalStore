package com.digitalstore.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.digitalstore.model.buyers.Buyer;
import java.util.List;

@Repository
public interface BuyerRepo extends MongoRepository<Buyer, String> {
    List<Buyer> findByBuyerEmail(String buyerEmail);
    List<Buyer> findByBuyerId(String buyerId);
    List<Buyer> findByBuyerName(String buyerName);
}
