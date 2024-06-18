package com.digitalstore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.digitalstore.model.status.VouchStatus;

@Document(collection = "vouch")
public class Vouch {
    @Id
    private String vouchId;
    private String vouchMessage;
    private String sellerId;
    private String buyerId;
    private VouchStatus vouchStatus;

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

    public VouchStatus getVouchStatus() {
        return vouchStatus;
    }

    public void setVouchStatus(VouchStatus vouchStatus) {
        this.vouchStatus = vouchStatus;
    }

}
