package com.digitalstore.model;

import org.springframework.data.annotation.Id;

import com.digitalstore.model.buyers.Status;

public class Vouch {
    @Id
    private String vouchId;
    private String vouchMessage;
    private String sellerId;
    private String buyerId;
    private Status vouchStatus;

    public String getVouchId() {
        return vouchId;
    }
    public void setVouchId(String vouchId) {
        this.vouchId = vouchId;
    }
    public String getVouchMessage() {
        return vouchMessage;
    }
    public void setVouchMessage(String vouchMessage) {
        this.vouchMessage = vouchMessage;
    }
    public String getSellerId() {
        return sellerId;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public String getBuyerId() {
        return buyerId;
    }
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
    public Status getVouchStatus() {
        return vouchStatus;
    }
    public void setVouchStatus(Status vouchStatus) {
        this.vouchStatus = vouchStatus;
    }
    
}
