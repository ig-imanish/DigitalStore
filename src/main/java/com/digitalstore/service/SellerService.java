package com.digitalstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.digitalstore.dao.SellerRepo;
import com.digitalstore.model.sellers.Seller;

@Service
public class SellerService {
    @Autowired
    private SellerRepo sellerRepo;

    public Seller saveSeller(Seller seller){
        Seller sellers = sellerRepo.save(seller);
        return sellers;
    }
    public void removeSeller(String sellerId){
        sellerRepo.deleteById(sellerId);
    }
    public boolean isSellerExist(String sellerId){
        return sellerRepo.existsById(sellerId);
    }

    public Seller findBySellerId(String sellerId) {
        return sellerRepo.findById(sellerId).get();
    }

    public Set<String> getAllSellerIds() {
        Set<String> existingCodes = new HashSet<>();
        sellerRepo.findAll().forEach(seller -> existingCodes.add(seller.getSellerId()));
        return existingCodes;
    }
    public List<Seller> findByBuyerEmail(String sellerEmail) {
        return sellerRepo.findBySellerEmail(sellerEmail);
    }
}
