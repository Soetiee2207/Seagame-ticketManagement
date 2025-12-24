package edu.thanglong.application.service;

import edu.thanglong.domain.entity.User;
import edu.thanglong.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    private final UserRepository userRepository;

    public WalletService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BigDecimal getBalance(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(User::getBalance).orElse(BigDecimal.ZERO);
    }

    @Transactional
    public BigDecimal deposit(Long userId, BigDecimal amount) throws Exception {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Số tiền nạp phải lớn hơn 0!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Người dùng không tồn tại!"));

        BigDecimal newBalance = user.getBalance().add(amount);
        user.setBalance(newBalance);
        userRepository.save(user);

        return newBalance;
    }

    @Transactional
    public BigDecimal deduct(Long userId, BigDecimal amount) throws Exception {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Số tiền phải lớn hơn 0!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Người dùng không tồn tại!"));

        if (user.getBalance().compareTo(amount) < 0) {
            throw new Exception("Số dư không đủ! Số dư hiện tại: " + user.getBalance() + " VNĐ");
        }

        BigDecimal newBalance = user.getBalance().subtract(amount);
        user.setBalance(newBalance);
        userRepository.save(user);

        return newBalance;
    }
}
