package com.digitalstore.controllers.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.digitalstore.dao.ProductRepo;
import com.digitalstore.model.Product;
import com.digitalstore.model.buyers.Buyer;
import com.digitalstore.model.sellers.Seller;
import com.digitalstore.service.BuyerService;
import com.digitalstore.service.SellerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GeneralController {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SellerService sellerService;
    @Autowired
    private BuyerService buyerService;

    @GetMapping("/")
    public String defaultPage(Model model, HttpSession session) {
        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);

        String typeOfUser = (String) session.getAttribute("typeOfUser");
        if(typeOfUser != null){
            if(typeOfUser.equals("buyer")){
                Buyer buyer = (Buyer)session.getAttribute("session-buyer");
                System.out.println(buyer);
                
                model.addAttribute("typeOfUser", "buyer");
                model.addAttribute("user", buyer);

            } else if(typeOfUser.equals("seller")){ 
                Seller seller = (Seller) session.getAttribute("session-seller");

                model.addAttribute("typeOfUser", "seller");
                model.addAttribute("user", seller);
            }else {
                return "index";
            }
        }
        return "index";
    }

    @GetMapping("/profile/{buyerId}")
    public String ProfilePage(@PathVariable String buyerId, Model model) {
    
        if(buyerId == null){
            return "redirect:/";
        }
        if(!buyerService.isBuyerExist(buyerId)){
            return "redirect:/";
        }
        Buyer buyer = buyerService.findByBuyerId(buyerId);
        model.addAttribute("buyer", buyer);
        return "profile";
    }
     @GetMapping("/profile")
    public String defaultProfilePage() {
        return "redirect:/";
    }

    @GetMapping("/dashboard/{sellerId}")
    public String DashboardPage(@PathVariable String sellerId, Model model) {
        if(sellerId == null){
            return "redirect:/";
        }
        if(!sellerService.isSellerExist(sellerId)){
            return "redirect:/";
        }

        Seller seller = sellerService.findBySellerId(sellerId);
        model.addAttribute("seller", seller);
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String defaultDashboardPage() {
        return "redirect:/";
    }

    @GetMapping("/login")
    public String defaultLoginPage(){
        return "login";
    }
}
