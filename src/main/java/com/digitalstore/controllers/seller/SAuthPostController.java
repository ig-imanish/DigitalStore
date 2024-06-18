package com.digitalstore.controllers.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.digitalstore.helpers.CodeGenerator;
import com.digitalstore.model.sellers.Seller;
import com.digitalstore.service.SellerService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller")
public class SAuthPostController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private CodeGenerator codeGenerator;

    @PostMapping("/login")
    public String sellerLoginPage(@ModelAttribute Seller seller, HttpSession session, Model model){
        List<Seller> sellers = sellerService.findByBuyerEmail(seller.getSellerEmail());
        if(sellers.isEmpty()){
            model.addAttribute("error", "seller Not Found");
            return "seller/login";
        } 

        Seller dbSeller = sellers.getFirst();

        if(dbSeller.getSellerEmail().equals(seller.getSellerEmail()) && dbSeller.getSellerPassword().equals(seller.getSellerPassword())){

            session.setAttribute("typeOfUser", "seller" );
            session.setAttribute("session-seller", dbSeller);
            return "redirect:/";

        }
        model.addAttribute("error", "Seller Not Found");
        return "seller/login";
    }

    @PostMapping("/register")
    public String sellerRegisterPage(@ModelAttribute Seller seller, Model model){
        boolean isexist = sellerService.isSellerExist(seller.getSellerEmail());
         if(isexist){
            model.addAttribute("error", "seller already exist with given email!");
            return "redirect:/login";
        }

        seller.setSellerId(codeGenerator.generateSellerCode());
        sellerService.saveSeller(seller);

        System.out.println(seller);
        return "redirect:/login";
    }
}
