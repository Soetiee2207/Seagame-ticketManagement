package edu.thanglong.presentation.controller;

import edu.thanglong.application.dto.BookingRequest;
import edu.thanglong.application.dto.MatchDTO;
import edu.thanglong.application.dto.SeatDTO;
import edu.thanglong.application.dto.TicketDTO;
import edu.thanglong.application.service.BookingService;
import edu.thanglong.application.service.MatchService;
import edu.thanglong.domain.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class BookingController {

    private final BookingService bookingService;
    private final MatchService matchService;

    public BookingController(BookingService bookingService, MatchService matchService) {
        this.bookingService = bookingService;
        this.matchService = matchService;
    }

    @GetMapping("/booking/{matchId}")
    public String bookingPage(@PathVariable Long matchId, Model model, HttpSession session) {
        Optional<MatchDTO> matchOpt = matchService.getMatchById(matchId);
        if (matchOpt.isEmpty()) {
            return "redirect:/";
        }

        List<SeatDTO> seats = bookingService.getStadiumMap(matchId);
        
        model.addAttribute("match", matchOpt.get());
        model.addAttribute("seats", seats);
        model.addAttribute("matchId", matchId);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        
        return "booking";
    }

    @PostMapping("/booking/book")
    public String bookTicket(@RequestParam Long seatId, 
                             @RequestParam Long matchId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        try {
            User currentUser = (User) session.getAttribute("currentUser");
            TicketDTO ticket = bookingService.bookTicket(currentUser.getId(), seatId, matchId);
            redirectAttributes.addFlashAttribute("success", "Đặt vé thành công! Mã vé: " + ticket.getTicketCode());
            return "redirect:/my-tickets";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/booking/" + matchId;
        }
    }

    @GetMapping("/my-tickets")
    public String myTickets(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        List<TicketDTO> tickets = bookingService.getUserTickets(currentUser.getId());
        
        model.addAttribute("tickets", tickets);
        model.addAttribute("currentUser", currentUser);
        
        return "my-tickets";
    }
}
