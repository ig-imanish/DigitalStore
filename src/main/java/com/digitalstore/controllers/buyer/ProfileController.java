package com.digitalstore.controllers.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.digitalstore.model.buyers.Buyer;
import com.digitalstore.service.BuyerService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private BuyerService buyerService;

    @GetMapping
    public String defaultProfilePage() {
        return "redirect:/";
    }

    @GetMapping("/{buyerId}")
    public String ProfilePage(@PathVariable String buyerId, Model model) {
        if (buyerId == null) {
            return "redirect:/";
        }
        if (!buyerService.isBuyerExist(buyerId)) {
            return "redirect:/";
        }
        Buyer buyer = buyerService.findByBuyerId(buyerId);
        model.addAttribute("buyer", buyer);
        return "profile";
    }
}
