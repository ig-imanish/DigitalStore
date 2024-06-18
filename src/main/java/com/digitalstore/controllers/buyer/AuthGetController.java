package com.digitalstore.controllers.buyer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.digitalstore.model.buyers.Buyer;

@Controller
@RequestMapping("/buyer")
public class AuthGetController {

    @GetMapping
    public String defaultBuyerPage(){
        return "redirect:/";
    }

    @GetMapping("/register")
    public String buyerRegisterPage(Model model){
        model.addAttribute("buyer", new Buyer());
        return "buyer/register";
    }

    @GetMapping("/login")
    public String buyerLoginPage(Model model){
         model.addAttribute("buyer", new Buyer());
        return "buyer/login";
    }
}
