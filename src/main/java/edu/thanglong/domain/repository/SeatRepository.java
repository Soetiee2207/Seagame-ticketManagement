package edu.thanglong.domain.repository;

import edu.thanglong.domain.entity.Seat;
import java.util.List;
import java.util.Optional;

public interface SeatRepository {
    List<Seat> findAll();
    Optional<Seat> findById(Long id);
    Seat save(Seat seat);
}
