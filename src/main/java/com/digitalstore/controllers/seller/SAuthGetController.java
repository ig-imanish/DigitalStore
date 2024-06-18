package com.digitalstore.controllers.seller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.digitalstore.model.sellers.Seller;

@Controller
@RequestMapping("/seller")
public class SAuthGetController {

    @GetMapping
    public String defaultBuyerPage(){
        return "redirect:/";
    }

    @GetMapping("/register")
    public String buyerRegisterPage(Model model){
        model.addAttribute("seller", new Seller());
        return "seller/register";
    }

    @GetMapping("/login")
    public String buyerLoginPage(Model model){
         model.addAttribute("seller", new Seller());
        return "seller/login";
    }
}
