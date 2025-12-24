package edu.thanglong.presentation.controller;

import edu.thanglong.application.dto.TicketDTO;
import edu.thanglong.application.service.AdminService;
import edu.thanglong.domain.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
}
