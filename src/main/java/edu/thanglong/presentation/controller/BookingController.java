package edu.thanglong.presentation.controller;

import edu.thanglong.application.dto.BookingRequest;
import edu.thanglong.application.dto.CheckoutDTO;
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

    @PostMapping("/checkout")
    public String checkoutPage(@RequestParam Long seatId, 
                               @RequestParam Long matchId,
                               Model model,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            CheckoutDTO checkout = bookingService.getCheckoutInfo(seatId, matchId);
            model.addAttribute("checkout", checkout);
            model.addAttribute("currentUser", session.getAttribute("currentUser"));
            return "checkout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/booking/" + matchId;
        }
    }

    @PostMapping("/checkout/confirm")
    public String confirmPayment(@RequestParam Long seatId,
                                 @RequestParam Long matchId,
                                 @RequestParam String paymentMethod,
                                 HttpSession session,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            User currentUser = (User) session.getAttribute("currentUser");
            TicketDTO ticket = bookingService.bookTicket(currentUser.getId(), seatId, matchId, paymentMethod);
            
            // Update session with new balance
            currentUser.setBalance(currentUser.getBalance().subtract(ticket.getPrice()));
            session.setAttribute("currentUser", currentUser);
            
            model.addAttribute("ticket", ticket);
            model.addAttribute("currentUser", currentUser);
            return "payment-success";
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

    @GetMapping("/my-tickets/{ticketId}")
    public String ticketDetail(@PathVariable Long ticketId, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        List<TicketDTO> tickets = bookingService.getUserTickets(currentUser.getId());
        
        // Find ticket by ID
        TicketDTO ticket = tickets.stream()
                .filter(t -> t.getId().equals(ticketId))
                .findFirst()
                .orElse(null);
        
        if (ticket == null) {
            return "redirect:/my-tickets";
        }
        
        model.addAttribute("ticket", ticket);
        model.addAttribute("currentUser", currentUser);
        
        return "ticket-detail";
    }
}

