package com.digitalstore.controllers.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.digitalstore.model.buyers.Buyer;
import com.digitalstore.service.BuyerService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private BuyerService buyerService;

    @GetMapping
    public String defaultProfilePage(HttpSession session) {
        if (!session.isNew()) {
            String typeOfUser = (String) session.getAttribute("typeOfUser");
            if (typeOfUser.equals("buyer")) {
                Buyer buyer = (Buyer) session.getAttribute("session-buyer");
                if (buyer != null) {
                    return "redirect/profile" + buyer.getBuyerId();
                }
                return "redirect:/";
            }
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/{buyerId}")
    public String ProfilePage(@PathVariable String buyerId, Model model, HttpSession session) {
        if (buyerId == null) {
            return "redirect:/";
        }
        if (buyerService.isBuyerExist(buyerId)) {
            Buyer buyer = buyerService.findByBuyerId(buyerId);
            model.addAttribute("buyer", buyer);
            return "profile";
        }
        model.addAttribute("error", "buyer not found");
        return "profile";
    }
}
