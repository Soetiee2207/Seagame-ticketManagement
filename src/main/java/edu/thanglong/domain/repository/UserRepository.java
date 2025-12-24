package edu.thanglong.domain.repository;

import edu.thanglong.domain.entity.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
    Optional<User> findById(Long id);
    boolean existsByUsername(String username);
}
