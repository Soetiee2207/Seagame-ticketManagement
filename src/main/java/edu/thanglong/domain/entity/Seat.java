package edu.thanglong.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "seat_code", length = 10)
    private String seatCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private SeatCategory category;

    public Seat() {}

    public Seat(Long id, String seatCode, SeatCategory category) {
        this.id = id;
        this.seatCode = seatCode;
        this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSeatCode() { return seatCode; }
    public void setSeatCode(String seatCode) { this.seatCode = seatCode; }

    public SeatCategory getCategory() { return category; }
    public void setCategory(SeatCategory category) { this.category = category; }

    public static SeatBuilder builder() { return new SeatBuilder(); }

    public static class SeatBuilder {
        private Long id;
        private String seatCode;
        private SeatCategory category;

        public SeatBuilder id(Long id) { this.id = id; return this; }
        public SeatBuilder seatCode(String seatCode) { this.seatCode = seatCode; return this; }
        public SeatBuilder category(SeatCategory category) { this.category = category; return this; }

        public Seat build() {
            return new Seat(id, seatCode, category);
        }
    }
}
