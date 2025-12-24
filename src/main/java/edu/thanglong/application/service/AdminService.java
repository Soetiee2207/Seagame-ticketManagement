package edu.thanglong.application.service;

import edu.thanglong.application.dto.TicketDTO;
import edu.thanglong.domain.entity.*;
import edu.thanglong.domain.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final TicketRepository ticketRepository;

    public AdminService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public TicketDTO verifyTicket(String ticketCode) throws Exception {
        Optional<Ticket> ticketOpt = ticketRepository.findByTicketCode(ticketCode);
        
        if (ticketOpt.isEmpty()) {
            throw new Exception("Không tìm thấy vé với mã: " + ticketCode);
        }
        
        Ticket ticket = ticketOpt.get();
        
        if (ticket.getStatus() == 1) {
            throw new Exception("Vé này đã được check-in trước đó!");
        }
        
        ticket.setStatus(1);
        Ticket savedTicket = ticketRepository.save(ticket);
        
        return convertToDTO(savedTicket);
    }

    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public long getTotalTickets() {
        return ticketRepository.findAll().size();
    }

    public long getCheckedInTickets() {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getStatus() == 1)
                .count();
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        Match match = ticket.getMatch();
        Seat seat = ticket.getSeat();
        SeatCategory category = seat.getCategory();
        
        return TicketDTO.builder()
                .id(ticket.getId())
                .ticketCode(ticket.getTicketCode())
                .matchName(match.getMatchName())
                .seatCode(seat.getSeatCode())
                .categoryName(category != null ? category.getCategoryName() : "Standard")
                .price(category != null ? category.getPrice() : java.math.BigDecimal.ZERO)
                .bookingTime(ticket.getBookingTime())
                .status(ticket.getStatus())
                .statusText(ticket.getStatus() == 0 ? "Mới đặt" : "Đã check-in")
                .location(match.getLocation())
                .matchTime(match.getStartTime())
                .build();
    }
}
