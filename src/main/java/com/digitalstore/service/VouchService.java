package com.digitalstore.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalstore.dao.VouchRepo;
import com.digitalstore.model.Vouch;

@Service
public class VouchService {
    @Autowired
    private VouchRepo vouchRepo;

    public Vouch saveVouch(Vouch vouch){
        return vouchRepo.save(vouch);
    }

    public Vouch removeVouch(String vouchId){
        Vouch vouch = vouchRepo.findById(vouchId).get();
        vouchRepo.deleteById(vouchId);
        return vouch;
    }
    public Vouch updateVouch(Vouch vouch){
        return vouchRepo.save(vouch);
    }
    public List<Vouch>  findVouchByBuyerId(String buyerId) {
        return vouchRepo.findByBuyerId(buyerId);
    }
    public List<Vouch>  findVouchBySellerId(String sellerId) {
        return vouchRepo.findBySellerId(sellerId);
    }
    public List<Vouch>  findAllVouch() {
        return vouchRepo.findAll();
    }
}
