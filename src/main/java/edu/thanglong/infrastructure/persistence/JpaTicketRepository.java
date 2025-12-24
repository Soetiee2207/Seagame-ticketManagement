package edu.thanglong.infrastructure.persistence;

import edu.thanglong.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaTicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketCode(String ticketCode);
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByMatchId(Long matchId);
    Optional<Ticket> findBySeatIdAndMatchId(Long seatId, Long matchId);
}
