package edu.thanglong.application.service;

import edu.thanglong.application.dto.SeatDTO;
import edu.thanglong.application.dto.TicketDTO;
import edu.thanglong.domain.entity.*;
import edu.thanglong.domain.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public BookingService(SeatRepository seatRepository, TicketRepository ticketRepository,
                          MatchRepository matchRepository, UserRepository userRepository) {
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    public List<SeatDTO> getStadiumMap(Long matchId) {
        List<Seat> allSeats = seatRepository.findAll();
        List<Ticket> bookedTickets = ticketRepository.findByMatchId(matchId);
        
        Set<Long> bookedSeatIds = bookedTickets.stream()
                .map(t -> t.getSeat().getId())
                .collect(Collectors.toSet());

        return allSeats.stream().map(seat -> {
            SeatCategory category = seat.getCategory();
            return SeatDTO.builder()
                    .id(seat.getId())
                    .seatCode(seat.getSeatCode())
                    .categoryName(category != null ? category.getCategoryName() : "Standard")
                    .price(category != null ? category.getPrice() : java.math.BigDecimal.ZERO)
                    .booked(bookedSeatIds.contains(seat.getId()))
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public TicketDTO bookTicket(Long userId, Long seatId, Long matchId) throws Exception {
        Optional<Ticket> existingTicket = ticketRepository.findBySeatIdAndMatchId(seatId, matchId);
        if (existingTicket.isPresent()) {
            throw new Exception("Ghế này đã được đặt rồi!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Người dùng không tồn tại!"));
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new Exception("Ghế không tồn tại!"));
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new Exception("Trận đấu không tồn tại!"));

        Ticket ticket = Ticket.builder()
                .ticketCode(UUID.randomUUID().toString())
                .user(user)
                .seat(seat)
                .match(match)
                .bookingTime(LocalDateTime.now())
                .status(0)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);
        
        return convertToDTO(savedTicket);
    }

    public List<TicketDTO> getUserTickets(Long userId) {
        List<Ticket> tickets = ticketRepository.findByUserId(userId);
        return tickets.stream().map(this::convertToDTO).collect(Collectors.toList());
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
