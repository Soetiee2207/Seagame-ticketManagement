package edu.thanglong.presentation.controller;

import edu.thanglong.application.dto.LoginRequest;
import edu.thanglong.application.dto.RegisterRequest;
import edu.thanglong.application.service.AuthService;
import edu.thanglong.domain.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/";
        }
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, 
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = authService.login(loginRequest);
        
        if (userOpt.isPresent()) {
            session.setAttribute("currentUser", userOpt.get());
            User user = userOpt.get();
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin";
            }
            return "redirect:/";
        }
        
        redirectAttributes.addFlashAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/";
        }
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest,
                          RedirectAttributes redirectAttributes) {
        try {
            authService.register(registerRequest);
            redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
