package edu.thanglong.domain.repository;

import edu.thanglong.domain.entity.SeatCategory;
import java.util.List;
import java.util.Optional;

public interface SeatCategoryRepository {
    List<SeatCategory> findAll();
    Optional<SeatCategory> findById(Long id);
    SeatCategory save(SeatCategory category);
}
