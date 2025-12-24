package edu.thanglong.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketDTO {
    private Long id;
    private String ticketCode;
    private String matchName;
    private String seatCode;
    private String categoryName;
    private BigDecimal price;
    private LocalDateTime bookingTime;
    private Integer status;
    private String statusText;
    private String location;
    private LocalDateTime matchTime;

    public TicketDTO() {}

    public TicketDTO(Long id, String ticketCode, String matchName, String seatCode, String categoryName,
                     BigDecimal price, LocalDateTime bookingTime, Integer status, String statusText,
                     String location, LocalDateTime matchTime) {
        this.id = id;
        this.ticketCode = ticketCode;
        this.matchName = matchName;
        this.seatCode = seatCode;
        this.categoryName = categoryName;
        this.price = price;
        this.bookingTime = bookingTime;
        this.status = status;
        this.statusText = statusText;
        this.location = location;
        this.matchTime = matchTime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    public String getMatchName() { return matchName; }
    public void setMatchName(String matchName) { this.matchName = matchName; }

    public String getSeatCode() { return seatCode; }
    public void setSeatCode(String seatCode) { this.seatCode = seatCode; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getStatusText() { return statusText; }
    public void setStatusText(String statusText) { this.statusText = statusText; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getMatchTime() { return matchTime; }
    public void setMatchTime(LocalDateTime matchTime) { this.matchTime = matchTime; }

    public static TicketDTOBuilder builder() { return new TicketDTOBuilder(); }

    public static class TicketDTOBuilder {
        private Long id;
        private String ticketCode;
        private String matchName;
        private String seatCode;
        private String categoryName;
        private BigDecimal price;
        private LocalDateTime bookingTime;
        private Integer status;
        private String statusText;
        private String location;
        private LocalDateTime matchTime;

        public TicketDTOBuilder id(Long id) { this.id = id; return this; }
        public TicketDTOBuilder ticketCode(String ticketCode) { this.ticketCode = ticketCode; return this; }
        public TicketDTOBuilder matchName(String matchName) { this.matchName = matchName; return this; }
        public TicketDTOBuilder seatCode(String seatCode) { this.seatCode = seatCode; return this; }
        public TicketDTOBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public TicketDTOBuilder price(BigDecimal price) { this.price = price; return this; }
        public TicketDTOBuilder bookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; return this; }
        public TicketDTOBuilder status(Integer status) { this.status = status; return this; }
        public TicketDTOBuilder statusText(String statusText) { this.statusText = statusText; return this; }
        public TicketDTOBuilder location(String location) { this.location = location; return this; }
        public TicketDTOBuilder matchTime(LocalDateTime matchTime) { this.matchTime = matchTime; return this; }

        public TicketDTO build() {
            return new TicketDTO(id, ticketCode, matchName, seatCode, categoryName, price,
                    bookingTime, status, statusText, location, matchTime);
        }
    }
}
