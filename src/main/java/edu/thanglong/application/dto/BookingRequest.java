package edu.thanglong.application.dto;

public class BookingRequest {
    private Long seatId;
    private Long matchId;

    public BookingRequest() {}

    public BookingRequest(Long seatId, Long matchId) {
        this.seatId = seatId;
        this.matchId = matchId;
    }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }
}
