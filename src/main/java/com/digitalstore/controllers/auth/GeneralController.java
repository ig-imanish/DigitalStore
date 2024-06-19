package com.digitalstore.controllers.auth;

import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.digitalstore.dao.ProductRepo;
import com.digitalstore.model.Product;
import com.digitalstore.model.buyers.Buyer;
import com.digitalstore.model.sellers.Seller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class GeneralController {
    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/")
    public String defaultPage(Model model, HttpSession session) {
        List<Product> products = productRepo.findAll();
        for(Product product: products){
            if (product.getProductImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(product.getProductImage().getData());
                product.setImageBase64(base64Image);
            }
        }
        model.addAttribute("products", products);

        String typeOfUser = (String) session.getAttribute("typeOfUser");
        if(typeOfUser != null){
            if(typeOfUser.equals("buyer")){
                Buyer buyer = (Buyer)session.getAttribute("session-buyer");
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

    @GetMapping("/login")
    public String defaultLoginPage(){
        return "login";
    }

   @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); 
        }

        return "redirect:/login"; 
    }

    @GetMapping("/register")
    public String defaultRegisterPage(){
        return "login";
    }
}
