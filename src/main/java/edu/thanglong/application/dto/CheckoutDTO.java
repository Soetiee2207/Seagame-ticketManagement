package edu.thanglong.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CheckoutDTO {
    private Long seatId;
    private Long matchId;
    private String seatCode;
    private String categoryName;
    private BigDecimal price;
    private String matchName;
    private LocalDateTime matchTime;
    private String location;

    public CheckoutDTO() {}

    public CheckoutDTO(Long seatId, Long matchId, String seatCode, String categoryName, 
                       BigDecimal price, String matchName, LocalDateTime matchTime, String location) {
        this.seatId = seatId;
        this.matchId = matchId;
        this.seatCode = seatCode;
        this.categoryName = categoryName;
        this.price = price;
        this.matchName = matchName;
        this.matchTime = matchTime;
        this.location = location;
    }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }

    public String getSeatCode() { return seatCode; }
    public void setSeatCode(String seatCode) { this.seatCode = seatCode; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getMatchName() { return matchName; }
    public void setMatchName(String matchName) { this.matchName = matchName; }

    public LocalDateTime getMatchTime() { return matchTime; }
    public void setMatchTime(LocalDateTime matchTime) { this.matchTime = matchTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public static CheckoutDTOBuilder builder() { return new CheckoutDTOBuilder(); }

    public static class CheckoutDTOBuilder {
        private Long seatId;
        private Long matchId;
        private String seatCode;
        private String categoryName;
        private BigDecimal price;
        private String matchName;
        private LocalDateTime matchTime;
        private String location;

        public CheckoutDTOBuilder seatId(Long seatId) { this.seatId = seatId; return this; }
        public CheckoutDTOBuilder matchId(Long matchId) { this.matchId = matchId; return this; }
        public CheckoutDTOBuilder seatCode(String seatCode) { this.seatCode = seatCode; return this; }
        public CheckoutDTOBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public CheckoutDTOBuilder price(BigDecimal price) { this.price = price; return this; }
        public CheckoutDTOBuilder matchName(String matchName) { this.matchName = matchName; return this; }
        public CheckoutDTOBuilder matchTime(LocalDateTime matchTime) { this.matchTime = matchTime; return this; }
        public CheckoutDTOBuilder location(String location) { this.location = location; return this; }

        public CheckoutDTO build() {
            return new CheckoutDTO(seatId, matchId, seatCode, categoryName, price, matchName, matchTime, location);
        }
    }
}
