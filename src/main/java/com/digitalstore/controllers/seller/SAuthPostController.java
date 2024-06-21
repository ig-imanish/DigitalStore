package com.digitalstore.controllers.seller;

import java.io.IOException;
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
    public String sellerLoginPage(@ModelAttribute Seller seller, HttpSession session, Model model) {
        List<Seller> sellers = sellerService.findByBuyerEmail(seller.getSellerEmail());
        if (sellers.isEmpty()) {
            model.addAttribute("error", "seller Not Found");
            return "seller/login";
        }

        Seller dbSeller = sellers.getFirst();

        if (dbSeller.getSellerEmail().equals(seller.getSellerEmail())
                && dbSeller.getSellerPassword().equals(seller.getSellerPassword())) {

            session.setAttribute("typeOfUser", "seller");
            session.setAttribute("session-seller", dbSeller);
            return "redirect:/";

        }

        model.addAttribute("error", "Seller Not Found");
        return "seller/login";
    }

    @PostMapping("/register")
    public String sellerRegisterPage(@ModelAttribute Seller seller, Model model,
            @RequestParam("avatar") MultipartFile sellerAvatar) {
        boolean isExist = sellerService.isSellerExistByEmail(seller.getSellerEmail());

        if (isExist) {
            model.addAttribute("error", "Seller already exists with the given email!");
            return "seller/register"; // Redirect to register page or handle appropriately
        }
        try {
            // Validate and process avatar upload
            if (sellerAvatar.getSize() > 10 * 1024 * 1024) { // 2MB limit (2 * 1024 * 1024 bytes)
                model.addAttribute("error", "File size exceeds 10MB limit.");
                return "seller/register"; // Redirect back to registration form with error
            }
            if (!sellerAvatar.isEmpty()) {
                Binary binaryImage = new Binary(BsonBinarySubType.BINARY, sellerAvatar.getBytes());
                seller.setSellerAvatar(binaryImage);
            } else {
                model.addAttribute("error", "Avatar file is required!");
                return "seller/register"; // Redirect to register page or handle appropriately
            }
        } catch (IOException e) {
            model.addAttribute("error", "Image upload failed: " + e.getMessage());
            return "seller/register"; // Redirect to register page or handle appropriately
        }
        seller.setSellerId(codeGenerator.generateSellerCode());
        sellerService.saveSeller(seller);
        return "redirect:/login";
    }

}
