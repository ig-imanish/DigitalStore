package com.digitalstore.controllers.buyer;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String buyerLoginPage(@ModelAttribute Buyer buyer, HttpSession session, Model model) {
        List<Buyer> buyers = buyerService.findByBuyerEmail(buyer.getBuyerEmail());
        if (buyers.isEmpty()) {
            model.addAttribute("error", "Buyer Not Found");
            return "buyer/login";
        }
        Buyer dbBuyer = buyers.getFirst();
        if (dbBuyer.getBuyerEmail().equals(buyer.getBuyerEmail())
                && dbBuyer.getBuyerPassword().equals(buyer.getBuyerPassword())) {

            session.setAttribute("typeOfUser", "buyer");
            System.out.println(dbBuyer);
            session.setAttribute("session-buyer", dbBuyer);
            return "redirect:/";

        }
        model.addAttribute("error", "Buyer Not Found");
        return "buyer/login";
    }

    @PostMapping("/register")
    public String buyerRegisterPage(@ModelAttribute Buyer buyer, Model model,
            @RequestParam("avatar") MultipartFile buyerAvatar) {
        boolean isexist = buyerService.isBuyerExistByEmail(buyer.getBuyerEmail());
        if (isexist) {
            model.addAttribute("error", "buyer already exist with given email!");
            return "redirect:/login";
        }
        try {
            if (buyerAvatar.getSize() > 10 * 1024 * 1024) {
                model.addAttribute("error", "File size exceeds 10MB limit.");
                return "buyer/register";
            }
            if (!buyerAvatar.isEmpty()) {
                Binary binaryImage = new Binary(BsonBinarySubType.BINARY, buyerAvatar.getBytes());
                buyer.setImageBase64(Base64.getEncoder().encodeToString(binaryImage.getData()));
                buyer.setBuyerAvatar(binaryImage);
            } else {
                model.addAttribute("error", "Avatar file is required!");
                return "buyer/register";
            }
        } catch (IOException e) {
            model.addAttribute("error", "Image upload failed: " + e.getMessage());
            return "buyer/register";
        }
        buyer.setBuyerId(codeGenerator.generateBuyerCode());
        buyerService.saveBuyer(buyer);
        return "redirect:/login";
    }

    @PostMapping("/edit")
    public String editBuyerAccount(@RequestParam String buyerName,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpSession session,
            @RequestParam("avatar") MultipartFile buyerAvatar,
            Model model) {

        Buyer buyer = (Buyer) session.getAttribute("session-buyer");
        if (buyer != null) {
            if (!buyer.getBuyerPassword().equals(oldPassword)) {
                model.addAttribute("error", "Your password does not match the old password.");
                return "buyer/edit";
            }
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "Your new password and confirm password do not match.");
                return "buyer/edit";
            }
            if (oldPassword.equals(newPassword)) {
                model.addAttribute("error", "Your new password cannot be the same as the old password.");
                return "buyer/edit";
            }
            if (!buyerName.isEmpty()) {
                buyer.setBuyerName(buyerName);
            }
            if (!newPassword.isEmpty()) {
                buyer.setBuyerPassword(newPassword);
            }
            try {
                if (buyerAvatar.getSize() > 10 * 1024 * 1024) {
                    model.addAttribute("error", "File size exceeds 10MB limit.");
                    return "buyer/edit";
                }
                if (!buyerAvatar.isEmpty()) {
                    Binary binaryImage = new Binary(BsonBinarySubType.BINARY, buyerAvatar.getBytes());
                    buyer.setBuyerAvatar(binaryImage);
                    buyer.setImageBase64(Base64.getEncoder().encodeToString(binaryImage.getData()));
                } else {
                    List<Buyer> existingBuyer = buyerService.findByBuyerEmail(buyer.getBuyerEmail());
                    if (!existingBuyer.isEmpty() && existingBuyer.get(0).getBuyerAvatar() != null) {
                        buyer.setBuyerAvatar(existingBuyer.get(0).getBuyerAvatar());
                    } else {
                        System.out.println("@PostMapping(\"/edit\") idk what to do check avatar here!");
                    }
                }
            } catch (IOException e) {
                model.addAttribute("error", "Image upload failed: " + e.getMessage());
                return "buyer/edit";
            }
            System.out.println("Buyer before saving: " + buyer);
            buyerService.saveBuyer(buyer);
            System.out.println("Buyer after saving: " + buyer);
        } else {
            model.addAttribute("error", "Session expired. Please log in again.");
            return "buyer/edit";
        }
        session.setAttribute("session-buyer", buyer);
        return "redirect:/profile/" + buyer.getBuyerId();
    }

}
