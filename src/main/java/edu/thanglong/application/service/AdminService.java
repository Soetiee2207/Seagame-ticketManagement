package edu.thanglong.application.service;

import edu.thanglong.application.dto.TicketDTO;
import edu.thanglong.domain.entity.*;
import edu.thanglong.domain.repository.MatchRepository;
import edu.thanglong.domain.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final TicketRepository ticketRepository;
    private final MatchRepository matchRepository;

    public AdminService(TicketRepository ticketRepository, MatchRepository matchRepository) {
        this.ticketRepository = ticketRepository;
        this.matchRepository = matchRepository;
    }

    // ========== MATCH MANAGEMENT ==========

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    @Transactional
    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    @Transactional
    public void deleteMatch(Long id) throws Exception {
        List<Ticket> tickets = ticketRepository.findByMatchId(id);
        if (!tickets.isEmpty()) {
            throw new Exception("Không thể xóa trận đấu này vì đã có " + tickets.size() + " vé được đặt!");
        }
        matchRepository.deleteById(id);
    }

    public long countTotalMatches() {
        return matchRepository.findAll().size();
    }

    @Transactional
    public TicketDTO verifyTicket(String ticketCode) throws Exception {
        Optional<Ticket> ticketOpt;
        
        // Support short code (8 chars) or full UUID
        if (ticketCode.length() < 36) {
            // Search by prefix (short code)
            List<Ticket> allTickets = ticketRepository.findAll();
            ticketOpt = allTickets.stream()
                    .filter(t -> t.getTicketCode().toUpperCase().startsWith(ticketCode.toUpperCase()))
                    .findFirst();
        } else {
            ticketOpt = ticketRepository.findByTicketCode(ticketCode);
        }
        
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
