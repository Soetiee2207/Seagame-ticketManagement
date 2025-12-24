package edu.thanglong.application.service;

import edu.thanglong.application.dto.LoginRequest;
import edu.thanglong.application.dto.RegisterRequest;
import edu.thanglong.domain.entity.User;
import edu.thanglong.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(request.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public User register(RegisterRequest request) throws Exception {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new Exception("Tên đăng nhập đã tồn tại!");
        }
        
        User newUser = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .role("USER")
                .build();
        
        return userRepository.save(newUser);
    }
}
