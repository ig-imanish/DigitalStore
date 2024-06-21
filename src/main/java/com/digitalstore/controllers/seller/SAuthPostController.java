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
            return "seller/register";
        }
        try {
            
            if (sellerAvatar.getSize() > 10 * 1024 * 1024) {
                model.addAttribute("error", "File size exceeds 10MB limit.");
                return "seller/register";
            }
            if (!sellerAvatar.isEmpty()) {
                Binary binaryImage = new Binary(BsonBinarySubType.BINARY, sellerAvatar.getBytes());
                seller.setSellerAvatar(binaryImage);
            } else {
                model.addAttribute("error", "Avatar file is required!");
                return "seller/register";
            }
        } catch (IOException e) {
            model.addAttribute("error", "Image upload failed: " + e.getMessage());
            return "seller/register";
        }
        seller.setSellerId(codeGenerator.generateSellerCode());
        sellerService.saveSeller(seller);
        return "redirect:/login";
    }

    @PostMapping("/edit")
    public String editSellerAccount(@RequestParam String sellerName, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, HttpSession session,  @RequestParam("avatar") MultipartFile sellerAvatar, Model model) {

       Seller seller = (Seller)session.getAttribute("session-seller");
        if(seller != null){
            if(!seller.getSellerPassword().equals(oldPassword)) {
                model.addAttribute("error", "your password do not matched to old");
                return "seller/edit";
            }
            if(!newPassword.equals(confirmPassword)){
                model.addAttribute("error", "your new password and confirm password is not same");
                return "seller/edit";
            }
            if(oldPassword.equals(newPassword)){
                model.addAttribute("error", "your new password can not be old password");
                return "seller/edit";
            }
            if(!sellerName.isEmpty()){

                seller.setSellerName(sellerName);
            }
            if(!newPassword.isEmpty()){
                seller.setSellerPassword(newPassword);
            }
            try {
            
            if (sellerAvatar.getSize() > 10 * 1024 * 1024) {
                model.addAttribute("error", "File size exceeds 10MB limit.");
                return "seller/edit";
            }
            if (!sellerAvatar.isEmpty()) {
                Binary binaryImage = new Binary(BsonBinarySubType.BINARY, sellerAvatar.getBytes());
                seller.setSellerAvatar(binaryImage);
            } else {
                List<Seller> existingSeller = sellerService.findByBuyerEmail(seller.getSellerEmail());
                if(existingSeller.getFirst().getSellerAvatar() != null){
                    seller.setSellerAvatar(existingSeller.getFirst().getSellerAvatar());
                } else {
                    System.out.println("@PostMapping(\"/edit\") idk what to do check avatar here!");
                }
            }
        } catch (IOException e) {
            model.addAttribute("error", "Image upload failed: " + e.getMessage());
            return "seller/edit";
        }
        System.out.println(seller);
            sellerService.saveSeller(seller);
        }
        return "redirect:/dashboard";
    }
    

}
