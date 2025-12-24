package edu.thanglong.domain.repository;

import edu.thanglong.domain.entity.Ticket;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    List<Ticket> findAll();
    Optional<Ticket> findById(Long id);
    Optional<Ticket> findByTicketCode(String ticketCode);
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByMatchId(Long matchId);
    Optional<Ticket> findBySeatIdAndMatchId(Long seatId, Long matchId);
    Ticket save(Ticket ticket);
}
