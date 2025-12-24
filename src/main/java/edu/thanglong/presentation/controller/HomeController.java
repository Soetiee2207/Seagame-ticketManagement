package edu.thanglong.presentation.controller;

import edu.thanglong.application.dto.MatchDTO;
import edu.thanglong.application.service.MatchService;
import edu.thanglong.domain.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final MatchService matchService;

    public HomeController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<MatchDTO> matches = matchService.getAllMatches();
        model.addAttribute("matches", matches);
        
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        
        return "home";
    }

    @GetMapping("/matches")
    public String matches(Model model, HttpSession session) {
        List<MatchDTO> matches = matchService.getAllMatches();
        model.addAttribute("matches", matches);
        
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        
        return "matches";
    }
}
