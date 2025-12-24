package edu.thanglong.infrastructure.persistence;

import edu.thanglong.domain.entity.SeatCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSeatCategoryRepository extends JpaRepository<SeatCategory, Long> {
}
