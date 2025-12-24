package edu.thanglong.application.dto;

import java.math.BigDecimal;

public class SeatDTO {
    private Long id;
    private String seatCode;
    private String categoryName;
    private BigDecimal price;
    private boolean booked;

    public SeatDTO() {}

    public SeatDTO(Long id, String seatCode, String categoryName, BigDecimal price, boolean booked) {
        this.id = id;
        this.seatCode = seatCode;
        this.categoryName = categoryName;
        this.price = price;
        this.booked = booked;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSeatCode() { return seatCode; }
    public void setSeatCode(String seatCode) { this.seatCode = seatCode; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public boolean isBooked() { return booked; }
    public void setBooked(boolean booked) { this.booked = booked; }

    public static SeatDTOBuilder builder() { return new SeatDTOBuilder(); }

    public static class SeatDTOBuilder {
        private Long id;
        private String seatCode;
        private String categoryName;
        private BigDecimal price;
        private boolean booked;

        public SeatDTOBuilder id(Long id) { this.id = id; return this; }
        public SeatDTOBuilder seatCode(String seatCode) { this.seatCode = seatCode; return this; }
        public SeatDTOBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public SeatDTOBuilder price(BigDecimal price) { this.price = price; return this; }
        public SeatDTOBuilder booked(boolean booked) { this.booked = booked; return this; }

        public SeatDTO build() {
            return new SeatDTO(id, seatCode, categoryName, price, booked);
        }
    }
}
