package com.digitalstore.controllers.auth;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.digitalstore.dao.ProductRepo;
import com.digitalstore.model.Product;
import com.digitalstore.model.buyers.Buyer;
import com.digitalstore.model.sellers.Seller;
import jakarta.servlet.http.HttpSession;

@Controller
public class GeneralController {
    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/")
    public String defaultPage(Model model, HttpSession session) {
        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);

        String typeOfUser = (String) session.getAttribute("typeOfUser");
        if(typeOfUser != null){
            if(typeOfUser.equals("buyer")){
                Buyer buyer = (Buyer)session.getAttribute("session-buyer");
                System.out.println("-------------------index page Buyer-----------------");
                System.out.println(buyer);
                System.out.println("-----------------------------------------");

                model.addAttribute("typeOfUser", "buyer");
                model.addAttribute("user", buyer);

            } else if(typeOfUser.equals("seller")){ 
                Seller seller = (Seller) session.getAttribute("session-seller");

                System.out.println("-------------------index page Seller-----------------");
                System.out.println(seller);
                System.out.println("-----------------------------------------");

                model.addAttribute("typeOfUser", "seller");
                model.addAttribute("user", seller);
            }else {
                return "index";
            }
        }
        return "index";
    }

    @GetMapping("/login")
    public String defaultLoginPage(){
        return "login";
    }
}
