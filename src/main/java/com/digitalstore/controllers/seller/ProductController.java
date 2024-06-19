package com.digitalstore.controllers.seller;

import java.io.IOException;
import java.util.Base64;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.digitalstore.model.Product;
import com.digitalstore.model.sellers.Seller;
import com.digitalstore.model.status.ProductStatus;
import com.digitalstore.service.ProductService;
import com.digitalstore.service.SellerService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String defaultProductPage() {
        return "redirect:/dashboard";
    }

    @GetMapping("/create")
    public String createProductForm(@RequestParam String sellerId, Model model, HttpSession session) {

        Seller sessionSeller = (Seller) session.getAttribute("session-seller");
        if (!sessionSeller.getSellerId().equals(sellerId)) {
            return "redirect:/";
        }

        model.addAttribute("sellerId", sellerId);
        model.addAttribute("product", new Product());
        return "seller/product/createProduct";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product,
            @RequestParam String sellerId,
            @RequestParam("productBinaryImage") MultipartFile productImage,
            Model model, HttpSession session) {

        Seller sessionSeller = (Seller) session.getAttribute("session-seller");
        if (!sessionSeller.getSellerId().equals(sellerId)) {
            return "redirect:/";
        }

        product.setSellerId(sellerId);
        product.setProductStatus(ProductStatus.UNSOLD);

        if (!productImage.isEmpty()) {
            try {
                // Convert MultipartFile to byte array
                byte[] imageData = productImage.getBytes();
                Binary binaryImage = new Binary(BsonBinarySubType.BINARY, imageData);
                product.setProductImage(binaryImage);
            } catch (IOException e) {
                model.addAttribute("error", "Image upload failed: " + e.getMessage());
                return "createProduct";
            }
        }

        productService.saveProduct(product);
        return "redirect:/dashboard?sellerId=" + sellerId;
    }

    @GetMapping("/update")
    public String updateProductForm(@RequestParam String id, Model model, HttpSession session) {

        Product product = productService.findByProductId(id);

        Seller sessionSeller = (Seller) session.getAttribute("session-seller");
        if (sessionSeller == null || !sessionSeller.getSellerId().equals(product.getSellerId())) {
            return "redirect:/";
        }

        if (product.getProductImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(product.getProductImage().getData());
            product.setImageBase64(base64Image);
        }

        model.addAttribute("product", product);
        return "seller/product/updateProduct";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product, Model model,
            @RequestParam("newProductImage") MultipartFile newProductImage, HttpSession session) {

        Seller sessionSeller = (Seller) session.getAttribute("session-seller");
        if (sessionSeller == null || !sessionSeller.getSellerId().equals(product.getSellerId())) {
            return "redirect:/";
        }

        if (!newProductImage.isEmpty()) {

            try {
                Binary binaryImage = new Binary(BsonBinarySubType.BINARY, newProductImage.getBytes());
                product.setProductImage(binaryImage);
            } catch (IOException e) {
                model.addAttribute("error", "Image upload failed: " + e.getMessage());
                return "createProduct";
            }
        } else {
            Product existingProduct = productService.findByProductId(product.getProductId());
            if (existingProduct != null && existingProduct.getProductImage() != null) {
                product.setProductImage(existingProduct.getProductImage());
            }
        }

        productService.saveProduct(product);
        return "redirect:/dashboard?sellerId=" + product.getSellerId();
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam String productId) {
        Product product = productService.findByProductId(productId);
        if (product != null) {
            productService.removeProduct(productId);
            return "redirect:/dashboard?sellerId=" + product.getSellerId();
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/order/{productId}")
    public String showOrderPage(@PathVariable String productId, Model model) {
        // Retrieve product details from ProductService
        if(productId == null){
            return "redirect:/";
        }
        Product product = productService.findByProductId(productId);
        if (product == null) {
            // Handle case where product is not found
            return "error"; // Redirect to error page or handle accordingly
        }

        // Retrieve seller details from SellerService
        Seller seller = sellerService.findBySellerId(product.getSellerId());
        if (seller == null) {
            // Handle case where seller is not found
            return "error"; // Redirect to error page or handle accordingly
        }

        // Add product and seller objects to the model to be accessed in Thymeleaf
        // template
        System.out.println(product);
        model.addAttribute("product1", product);
        model.addAttribute("seller", seller);

        // Return the order page template
        return "seller/product/order";
    }
}
