package edu.thanglong.infrastructure.persistence;

import edu.thanglong.domain.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMatchRepository extends JpaRepository<Match, Long> {
}
