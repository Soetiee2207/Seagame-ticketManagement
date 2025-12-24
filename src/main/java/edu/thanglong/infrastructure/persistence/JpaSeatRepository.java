package edu.thanglong.infrastructure.persistence;

import edu.thanglong.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSeatRepository extends JpaRepository<Seat, Long> {
}
