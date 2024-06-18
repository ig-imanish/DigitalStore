package com.digitalstore.controllers.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.digitalstore.dao.ProductRepo;
import com.digitalstore.model.Product;
import com.digitalstore.model.status.ProductStatus;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepo productRepository;

    @GetMapping("/create")
    public String createProductForm(@RequestParam String sellerId, Model model) {
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("product", new Product());
        return "seller/product/createProduct";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, @RequestParam String sellerId) {
        product.setSellerId(sellerId);
        product.setProductStatus(ProductStatus.UNSOLD);
        productRepository.save(product);
        return "redirect:/dashboard?sellerId=" + sellerId;
    }

    @GetMapping("/edit")
    public String editProductForm(@RequestParam String id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "seller/product/editProduct";
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute Product product) {
        
        productRepository.save(product);
        return "redirect:/dashboard?sellerId=" + product.getSellerId();
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            return "redirect:/dashboard?sellerId=" + product.getSellerId();
        }
        return "redirect:/dashboard";
    }
}

