package com.digitalstore.model.sellers;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.digitalstore.model.Product;
import com.digitalstore.model.Vouch;

@Document(collection = "sellers")
public class Seller {
    @Id
    private String sellerId;
    private String sellerName;
    private String sellerEmail;
    private String sellerPassword;
    private List<Product> sellerProducts;
    private List<Vouch> sellerVouches;

    public String getSellerId() {
        return sellerId;
    }
    public void setSellerId(String userId) {
        this.sellerId = userId;
    }
    public String getSellerName() {
        return sellerName;
    }
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
    public String getSellerEmail() {
        return sellerEmail;
    }
    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }
    public String getSellerPassword() {
        return sellerPassword;
    }
    public void setSellerPassword(String sellerPassword) {
        this.sellerPassword = sellerPassword;
    }
    public List<Product> getSellerProducts() {
        return sellerProducts;
    }
    public void setSellerProducts(List<Product> sellerProducts) {
        this.sellerProducts = sellerProducts;
    }
    public List<Vouch> getSellerVouches() {
        return sellerVouches;
    }
    public void setSellerVouches(List<Vouch> sellerVouches) {
        this.sellerVouches = sellerVouches;
    }
    @Override
    public String toString() {
        return "Seller [sellerId=" + sellerId + ", sellerName=" + sellerName + ", sellerEmail=" + sellerEmail
                + ", sellerPassword=" + sellerPassword + ", sellerProducts=" + sellerProducts + ", sellerVouches="
                + sellerVouches + "]";
    }

    
}
