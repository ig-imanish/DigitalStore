package com.digitalstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalstore.dao.BuyerRepo;
import com.digitalstore.model.buyers.Buyer;

@Service
public class BuyerService {
    @Autowired
    private BuyerRepo buyerRepo;

    public Buyer saveBuyer(Buyer buyer){
        return buyerRepo.save(buyer);
    }
    public void removeBuyer(String buyerId){
        buyerRepo.deleteById(buyerId);
    }

    public boolean isBuyerExist(String buyerId){
        return buyerRepo.existsById(buyerId);
    }
     public boolean isBuyerExistByEmail(String buyerEmail){
        List<Buyer> buyers = buyerRepo.findByBuyerEmail(buyerEmail);
        for(Buyer buyer : buyers){
            if(buyer.getBuyerEmail().equals(buyerEmail)){
                return true;
            }
        }
        return false;
    }

    public List<Buyer> findByBuyerEmail(String buyerEmail) {
       return buyerRepo.findByBuyerEmail(buyerEmail);
    }
    public Buyer findByBuyerId(String buyerId) {
        return buyerRepo.findById(buyerId).get();
    }

    public Set<String> getAllBuyerIds() {
        Set<String> existingCodes = new HashSet<>();
        buyerRepo.findAll().forEach(buyer -> existingCodes.add(buyer.getBuyerId()));
        return existingCodes;
    }

}
