package edu.thanglong.domain.repository;

import edu.thanglong.domain.entity.Match;
import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    List<Match> findAll();
    Optional<Match> findById(Long id);
    Match save(Match match);
    void deleteById(Long id);
}
