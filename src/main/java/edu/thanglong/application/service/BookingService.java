package edu.thanglong.application.service;

import edu.thanglong.application.dto.CheckoutDTO;
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

    public CheckoutDTO getCheckoutInfo(Long seatId, Long matchId) throws Exception {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new Exception("Ghế không tồn tại!"));
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new Exception("Trận đấu không tồn tại!"));
        
        Optional<Ticket> existingTicket = ticketRepository.findBySeatIdAndMatchId(seatId, matchId);
        if (existingTicket.isPresent()) {
            throw new Exception("Ghế này đã được đặt rồi!");
        }

        SeatCategory category = seat.getCategory();
        
        return CheckoutDTO.builder()
                .seatId(seatId)
                .matchId(matchId)
                .seatCode(seat.getSeatCode())
                .categoryName(category != null ? category.getCategoryName() : "Standard")
                .price(category != null ? category.getPrice() : java.math.BigDecimal.ZERO)
                .matchName(match.getMatchName())
                .matchTime(match.getStartTime())
                .location(match.getLocation())
                .build();
    }

    @Transactional
    public TicketDTO bookTicket(Long userId, Long seatId, Long matchId, String paymentMethod) throws Exception {
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

        // Get ticket price from seat category
        java.math.BigDecimal price = seat.getCategory() != null 
                ? seat.getCategory().getPrice() 
                : java.math.BigDecimal.ZERO;

        // Check and deduct balance
        if (user.getBalance().compareTo(price) < 0) {
            throw new Exception("Số dư không đủ! Cần: " + price + " VNĐ, Số dư: " + user.getBalance() + " VNĐ. Vui lòng nạp thêm tiền.");
        }

        // Deduct balance
        user.setBalance(user.getBalance().subtract(price));
        userRepository.save(user);

        LocalDateTime now = LocalDateTime.now();
        
        Ticket ticket = Ticket.builder()
                .ticketCode(UUID.randomUUID().toString())
                .user(user)
                .seat(seat)
                .match(match)
                .bookingTime(now)
                .status(0)
                .paymentMethod("WALLET")
                .paymentStatus(1) // 1 = Paid
                .paymentTime(now)
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
        
        String paymentStatusText = "Chưa xác định";
        if (ticket.getPaymentStatus() != null) {
            switch (ticket.getPaymentStatus()) {
                case 0: paymentStatusText = "Chờ thanh toán"; break;
                case 1: paymentStatusText = "Đã thanh toán"; break;
                case 2: paymentStatusText = "Đã hủy"; break;
            }
        }
        
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
                .paymentMethod(ticket.getPaymentMethod())
                .paymentStatus(ticket.getPaymentStatus())
                .paymentStatusText(paymentStatusText)
                .build();
    }
}

