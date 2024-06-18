package com.digitalstore.controllers.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.digitalstore.helpers.CodeGenerator;
import com.digitalstore.model.buyers.Buyer;
import com.digitalstore.service.BuyerService;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/buyer")
public class BAuthPostController {
    @Autowired
    private BuyerService buyerService;

    @Autowired
    private CodeGenerator codeGenerator;

    @PostMapping("/login")
    public String buyerLoginPage(@ModelAttribute Buyer buyer, HttpSession session, Model model){
        List<Buyer> buyers = buyerService.findByBuyerEmail(buyer.getBuyerEmail());
        if(buyers.isEmpty()){
            model.addAttribute("error", "Buyer Not Found");
            return "buyer/login";
        } 

        Buyer dbBuyer = buyers.getFirst();

        if(dbBuyer.getBuyerEmail().equals(buyer.getBuyerEmail()) && dbBuyer.getBuyerPassword().equals(buyer.getBuyerPassword())){

            session.setAttribute("typeOfUser", "buyer" );
            session.setAttribute("session-buyer", dbBuyer);
            return "redirect:/";

        }
        model.addAttribute("error", "Buyer Not Found");
        return "buyer/login";
    }

    @PostMapping("/register")
    public String buyerRegisterPage(@ModelAttribute Buyer buyer, Model model){
        boolean isexist = buyerService.isBuyerExist(buyer.getBuyerEmail());
         if(isexist){
            model.addAttribute("error", "buyer already exist with given email!");
            return "redirect:/login";
        }

        buyer.setBuyerId(codeGenerator.generateBuyerCode());
        buyerService.saveBuyer(buyer);

        System.out.println(buyer);
        return "redirect:/login";
    }
}
