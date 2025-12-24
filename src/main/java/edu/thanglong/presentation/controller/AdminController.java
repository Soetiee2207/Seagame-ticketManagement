package edu.thanglong.presentation.controller;

import edu.thanglong.application.dto.TicketDTO;
import edu.thanglong.application.service.AdminService;
import edu.thanglong.domain.entity.Match;
import edu.thanglong.domain.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String dashboard(Model model, HttpSession session) {
        model.addAttribute("totalTickets", adminService.getTotalTickets());
        model.addAttribute("checkedInTickets", adminService.getCheckedInTickets());
        model.addAttribute("totalMatches", adminService.countTotalMatches());
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "admin/dashboard";
    }

    @GetMapping("/tickets")
    public String tickets(Model model, HttpSession session) {
        List<TicketDTO> tickets = adminService.getAllTickets();
        model.addAttribute("tickets", tickets);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "admin/tickets";
    }

    @GetMapping("/checkin")
    public String checkinPage(Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "admin/checkin";
    }

    @PostMapping("/checkin")
    public String checkin(@RequestParam String ticketCode, 
                          RedirectAttributes redirectAttributes) {
        try {
            TicketDTO ticket = adminService.verifyTicket(ticketCode);
            redirectAttributes.addFlashAttribute("success", 
                "Check-in thành công! Ghế: " + ticket.getSeatCode() + " - Trận: " + ticket.getMatchName());
            redirectAttributes.addFlashAttribute("checkedTicket", ticket);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/checkin";
    }

    // ========== MATCH MANAGEMENT ==========

    @GetMapping("/matches")
    public String listMatches(Model model, HttpSession session) {
        List<Match> matches = adminService.getAllMatches();
        model.addAttribute("matches", matches);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "admin/matches";
    }

    @GetMapping("/matches/add")
    public String addMatchForm(Model model, HttpSession session) {
        model.addAttribute("match", new Match());
        model.addAttribute("isEdit", false);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "admin/match-form";
    }

    @GetMapping("/matches/edit/{id}")
    public String editMatchForm(@PathVariable Long id, Model model, 
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<Match> matchOpt = adminService.getMatchById(id);
        if (matchOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy trận đấu!");
            return "redirect:/admin/matches";
        }
        model.addAttribute("match", matchOpt.get());
        model.addAttribute("isEdit", true);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "admin/match-form";
    }

    @PostMapping("/matches/save")
    public String saveMatch(@RequestParam(required = false) Long id,
                            @RequestParam String matchName,
                            @RequestParam String startTime,
                            @RequestParam String location,
                            RedirectAttributes redirectAttributes) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            
            Match match = Match.builder()
                    .id(id)
                    .matchName(matchName)
                    .startTime(dateTime)
                    .location(location)
                    .build();
            
            adminService.saveMatch(match);
            redirectAttributes.addFlashAttribute("success", 
                id == null ? "Thêm trận đấu thành công!" : "Cập nhật trận đấu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/matches";
    }

    @PostMapping("/matches/delete/{id}")
    public String deleteMatch(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteMatch(id);
            redirectAttributes.addFlashAttribute("success", "Xóa trận đấu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/matches";
    }
}

