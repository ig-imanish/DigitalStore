package com.digitalstore.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.digitalstore.model.Vouch;
import java.util.List;


public interface VouchRepo extends MongoRepository<Vouch, String>{
    List<Vouch> findBySellerId(String sellerId);
    List<Vouch> findByBuyerId(String buyerId);
}
