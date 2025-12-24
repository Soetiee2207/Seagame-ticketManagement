package edu.thanglong.infrastructure.config;

import edu.thanglong.domain.entity.*;
import edu.thanglong.domain.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final SeatCategoryRepository seatCategoryRepository;
    private final SeatRepository seatRepository;

    public DataInitializer(UserRepository userRepository, 
                          MatchRepository matchRepository,
                          SeatCategoryRepository seatCategoryRepository,
                          SeatRepository seatRepository) {
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
        this.seatCategoryRepository = seatCategoryRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Chỉ khởi tạo nếu chưa có admin
        if (!userRepository.existsByUsername("admin")) {
            System.out.println("=== Khởi tạo dữ liệu mẫu cho SEA Games Ticketing ===");
            
            initUsers();
            initSeatCategories();
            initMatches();
            initSeats();
            
            System.out.println("=== Hoàn tất khởi tạo dữ liệu! ===");
        } else {
            System.out.println("=== Database đã có dữ liệu, bỏ qua khởi tạo ===");
        }
    }

    private void initUsers() {
        // Tạo Admin
        User admin = User.builder()
            .username("admin")
            .password("admin123")
            .fullName("Administrator")
            .role("ADMIN")
            .balance(new BigDecimal("0"))
            .build();
        userRepository.save(admin);

        // Tạo Staff
        User staff = User.builder()
            .username("staff")
            .password("staff123")
            .fullName("Nhân viên kiểm vé")
            .role("STAFF")
            .balance(new BigDecimal("0"))
            .build();
        userRepository.save(staff);

        // Tạo User demo
        User user1 = User.builder()
            .username("user1")
            .password("user123")
            .fullName("Nguyễn Văn A")
            .role("USER")
            .balance(new BigDecimal("5000000"))
            .build();
        userRepository.save(user1);

        User user2 = User.builder()
            .username("user2")
            .password("user123")
            .fullName("Trần Thị B")
            .role("USER")
            .balance(new BigDecimal("3000000"))
            .build();
        userRepository.save(user2);

        System.out.println("✓ Đã tạo 4 tài khoản (admin, staff, user1, user2)");
    }

    private void initSeatCategories() {
        SeatCategory vip = SeatCategory.builder()
            .categoryName("VIP")
            .price(new BigDecimal("500000"))
            .description("Ghế VIP - Vị trí tốt nhất, gần sân nhất")
            .build();
        seatCategoryRepository.save(vip);

        SeatCategory standard = SeatCategory.builder()
            .categoryName("Standard")
            .price(new BigDecimal("300000"))
            .description("Ghế tiêu chuẩn - Vị trí trung tâm")
            .build();
        seatCategoryRepository.save(standard);

        SeatCategory economy = SeatCategory.builder()
            .categoryName("Economy")
            .price(new BigDecimal("150000"))
            .description("Ghế tiết kiệm - Vị trí xa sân")
            .build();
        seatCategoryRepository.save(economy);

        System.out.println("✓ Đã tạo 3 loại ghế (VIP, Standard, Economy)");
    }

    private void initMatches() {
        // Trận 1
        Match match1 = Match.builder()
            .matchName("Bóng đá: Việt Nam vs Thái Lan")
            .startTime(LocalDateTime.of(2025, 5, 15, 19, 0))
            .location("Sân vận động Mỹ Đình, Hà Nội")
            .build();
        matchRepository.save(match1);

        // Trận 2
        Match match2 = Match.builder()
            .matchName("Bóng đá: Việt Nam vs Indonesia")
            .startTime(LocalDateTime.of(2025, 5, 18, 19, 30))
            .location("Sân vận động Mỹ Đình, Hà Nội")
            .build();
        matchRepository.save(match2);

        // Trận 3
        Match match3 = Match.builder()
            .matchName("Bơi lội: Chung kết 100m tự do")
            .startTime(LocalDateTime.of(2025, 5, 16, 14, 0))
            .location("Trung tâm thể thao dưới nước, TP.HCM")
            .build();
        matchRepository.save(match3);

        // Trận 4
        Match match4 = Match.builder()
            .matchName("Điền kinh: Chung kết 100m nam")
            .startTime(LocalDateTime.of(2025, 5, 17, 16, 30))
            .location("Sân vận động Quốc gia, Hà Nội")
            .build();
        matchRepository.save(match4);

        // Trận 5
        Match match5 = Match.builder()
            .matchName("Cầu lông: Chung kết đơn nam")
            .startTime(LocalDateTime.of(2025, 5, 20, 18, 0))
            .location("Nhà thi đấu Đa năng, Đà Nẵng")
            .build();
        matchRepository.save(match5);

        // Trận 6
        Match match6 = Match.builder()
            .matchName("Bóng chuyền: Việt Nam vs Philippines")
            .startTime(LocalDateTime.of(2025, 5, 19, 20, 0))
            .location("Nhà thi đấu Phú Thọ, TP.HCM")
            .build();
        matchRepository.save(match6);

        System.out.println("✓ Đã tạo 6 trận đấu SEA Games");
    }

    private void initSeats() {
        SeatCategory vip = seatCategoryRepository.findAll().stream()
            .filter(c -> "VIP".equals(c.getCategoryName()))
            .findFirst().orElse(null);
        SeatCategory standard = seatCategoryRepository.findAll().stream()
            .filter(c -> "Standard".equals(c.getCategoryName()))
            .findFirst().orElse(null);
        SeatCategory economy = seatCategoryRepository.findAll().stream()
            .filter(c -> "Economy".equals(c.getCategoryName()))
            .findFirst().orElse(null);

        if (vip == null || standard == null || economy == null) {
            System.out.println("⚠ Không tìm thấy loại ghế, bỏ qua tạo ghế");
            return;
        }

        // Tạo ghế VIP: A1-A20
        for (int i = 1; i <= 20; i++) {
            Seat seat = Seat.builder()
                .seatCode("A" + i)
                .category(vip)
                .build();
            seatRepository.save(seat);
        }

        // Tạo ghế Standard: B1-B30, C1-C30
        for (int i = 1; i <= 30; i++) {
            Seat seatB = Seat.builder().seatCode("B" + i).category(standard).build();
            Seat seatC = Seat.builder().seatCode("C" + i).category(standard).build();
            seatRepository.save(seatB);
            seatRepository.save(seatC);
        }

        // Tạo ghế Economy: D1-D40, E1-E40
        for (int i = 1; i <= 40; i++) {
            Seat seatD = Seat.builder().seatCode("D" + i).category(economy).build();
            Seat seatE = Seat.builder().seatCode("E" + i).category(economy).build();
            seatRepository.save(seatD);
            seatRepository.save(seatE);
        }

        System.out.println("✓ Đã tạo 180 ghế (20 VIP, 60 Standard, 80 Economy)");
    }
}
