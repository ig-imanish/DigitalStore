package com.digitalstore.helpers;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitalstore.service.BuyerService;
import com.digitalstore.service.SellerService;

@Component
public class CodeGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 12;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;

    private String generateCode(String prefix) {
        Set<String> existingCodes = new HashSet<>();
        existingCodes.addAll(buyerService.getAllBuyerIds());
        existingCodes.addAll(sellerService.getAllSellerIds());

        String code;
        do {
            code = generateRandomCode();
        } while (existingCodes.contains(prefix + "-" + code));
        existingCodes.add(prefix + "-" + code);
        return prefix + "-" + code;
    }

    private String generateRandomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH + 3); // +3 for the dashes
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (i > 0 && i % 4 == 0) {
                code.append('-');
            }
            code.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return code.toString().toUpperCase();
    }

    public String generateBuyerCode() {
        return generateCode("BUYER");
    }

    public String generateSellerCode() {
        return generateCode("SELLER");
    }
}
