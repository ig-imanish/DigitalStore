package com.digitalstore.model.buyers;

import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.digitalstore.model.Vouch;

@Document(collection = "buyers")
public class Buyer {
    @Id
    private String buyerId;
    private String buyerName;
    private String buyerEmail;
    private String buyerPassword;
    private List<Vouch> buyerVouches;
    private Binary buyerAvatar;
    private transient String imageBase64;
    
    
    public Binary getBuyerAvatar() {
        return buyerAvatar;
    }

    public void setBuyerAvatar(Binary buyerAvatar) {
        this.buyerAvatar = buyerAvatar;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerPassword() {
        return buyerPassword;
    }

    public void setBuyerPassword(String buyerPassword) {
        this.buyerPassword = buyerPassword;
    }

    public List<Vouch> getBuyerVouches() {
        return buyerVouches;
    }

    public void setBuyerVouches(List<Vouch> buyerVouches) {
        this.buyerVouches = buyerVouches;
    }

    @Override
    public String toString() {
        return "Buyer [buyerId=" + buyerId + ", buyerName=" + buyerName + ", buyerEmail=" + buyerEmail
                + ", buyerPassword=" + buyerPassword + ", buyerVouches=" + buyerVouches + ", buyerAvatar=" + buyerAvatar
                + "]";
    }
}
