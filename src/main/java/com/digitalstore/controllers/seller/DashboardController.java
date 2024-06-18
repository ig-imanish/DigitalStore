package com.digitalstore.controllers.seller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.digitalstore.model.Product;
import com.digitalstore.model.Vouch;
import com.digitalstore.model.sellers.Seller;
import com.digitalstore.model.status.ProductStatus;
import com.digitalstore.model.status.VouchStatus;
import com.digitalstore.service.ProductService;
import com.digitalstore.service.SellerService;
import com.digitalstore.service.VouchService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private VouchService vouchService;

    @GetMapping
    public String defaultDashboardPage(HttpSession session) {
        Seller seller = (Seller)session.getAttribute("session-seller");
        if(seller == null){
            return "redirect:/";
        }
        if(sellerService.isSellerExist(seller.getSellerId())){
            return "redirect:/dashboard/" + seller.getSellerId();
        }
        return "redirect:/";
    }

    @GetMapping("/{sellerId}")
    public String DashboardPage(@PathVariable String sellerId, Model model, HttpSession session) {
        if (sellerId == null) {
            return "redirect:/";
        }
        if (!sellerService.isSellerExist(sellerId)) {
            return "redirect:/";
        }
        Seller sessionSeller = (Seller) session.getAttribute("session-seller");
        
        if(sessionSeller == null || !sessionSeller.getSellerId().equals(sellerId)){
            return "redirect:/";
        }

        Seller seller = sellerService.findBySellerId(sellerId);
        List<Vouch> vouches = vouchService.findVouchBySellerId(sellerId);
        List<Product> products = productService.findBySellerId(sellerId);

        if (seller != null) {
            int totalProducts = products.size();

            int soldProducts = (int) products.stream()
                .filter(product -> product.getProductStatus() == ProductStatus.SOLD)
                .count();
            int unsoldProducts = (int) products.stream()
                .filter(product -> product.getProductStatus() == ProductStatus.UNSOLD)
                .count();

            int verifiedVouches = (int) vouches.stream()
                .filter(vouch -> vouch.getVouchStatus() == VouchStatus.VERIFIED)
                .count();
            int pendingVouches = (int) vouches.stream()
                .filter(vouch -> vouch.getVouchStatus() == VouchStatus.PENDING)
                .count();
            int declinedVouches = (int) vouches.stream()
                .filter(vouch -> vouch.getVouchStatus() == VouchStatus.DECLINED)
                .count();

            model.addAttribute("seller", seller);
            model.addAttribute("totalProducts", totalProducts);
            model.addAttribute("soldProducts", soldProducts);
            model.addAttribute("unsoldProducts", unsoldProducts);

            model.addAttribute("verifiedVouches", verifiedVouches);
            model.addAttribute("pendingVouches", pendingVouches);
            model.addAttribute("declinedVouches", declinedVouches);
        }

        Seller seller2 = sellerService.findBySellerId(sellerId);
        List<Product> allProducts = productService.findBySellerId(sellerId);

        model.addAttribute("products", allProducts);
        model.addAttribute("seller", seller2);
        return "dashboard";
    }
}
