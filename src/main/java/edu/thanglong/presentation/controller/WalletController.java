package edu.thanglong.presentation.controller;

import edu.thanglong.application.service.WalletService;
import edu.thanglong.domain.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public String walletPage(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        BigDecimal balance = walletService.getBalance(currentUser.getId());
        
        model.addAttribute("balance", balance);
        model.addAttribute("currentUser", currentUser);
        return "wallet";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam BigDecimal amount,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        try {
            User currentUser = (User) session.getAttribute("currentUser");
            BigDecimal newBalance = walletService.deposit(currentUser.getId(), amount);
            
            // Update session user balance
            currentUser.setBalance(newBalance);
            session.setAttribute("currentUser", currentUser);
            
            redirectAttributes.addFlashAttribute("success", 
                "Nạp tiền thành công! Số dư mới: " + newBalance + " VNĐ");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/wallet";
    }
}
