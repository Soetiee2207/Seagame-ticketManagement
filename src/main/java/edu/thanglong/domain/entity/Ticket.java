package edu.thanglong.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ticket_code", length = 36, unique = true)
    private String ticketCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;
    
    @Column(name = "booking_time")
    private LocalDateTime bookingTime;
    
    @Column
    private Integer status;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "payment_status")
    private Integer paymentStatus; // 0 = Pending, 1 = Paid, 2 = Cancelled

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    public Ticket() {}

    public Ticket(Long id, String ticketCode, User user, Seat seat, Match match, 
                  LocalDateTime bookingTime, Integer status, String paymentMethod, 
                  Integer paymentStatus, LocalDateTime paymentTime) {
        this.id = id;
        this.ticketCode = ticketCode;
        this.user = user;
        this.seat = seat;
        this.match = match;
        this.bookingTime = bookingTime;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }

    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Integer getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(Integer paymentStatus) { this.paymentStatus = paymentStatus; }

    public LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }

    public static TicketBuilder builder() { return new TicketBuilder(); }

    public static class TicketBuilder {
        private Long id;
        private String ticketCode;
        private User user;
        private Seat seat;
        private Match match;
        private LocalDateTime bookingTime;
        private Integer status;
        private String paymentMethod;
        private Integer paymentStatus;
        private LocalDateTime paymentTime;

        public TicketBuilder id(Long id) { this.id = id; return this; }
        public TicketBuilder ticketCode(String ticketCode) { this.ticketCode = ticketCode; return this; }
        public TicketBuilder user(User user) { this.user = user; return this; }
        public TicketBuilder seat(Seat seat) { this.seat = seat; return this; }
        public TicketBuilder match(Match match) { this.match = match; return this; }
        public TicketBuilder bookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; return this; }
        public TicketBuilder status(Integer status) { this.status = status; return this; }
        public TicketBuilder paymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public TicketBuilder paymentStatus(Integer paymentStatus) { this.paymentStatus = paymentStatus; return this; }
        public TicketBuilder paymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; return this; }

        public Ticket build() {
            return new Ticket(id, ticketCode, user, seat, match, bookingTime, status, 
                              paymentMethod, paymentStatus, paymentTime);
        }
    }
}

