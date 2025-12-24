package edu.thanglong.infrastructure.config;

import edu.thanglong.infrastructure.persistence.*;
import edu.thanglong.domain.repository.*;
import edu.thanglong.domain.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;
import java.util.Optional;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/booking/**", "/my-tickets", "/admin/**")
                .excludePathPatterns("/login", "/register", "/", "/matches", "/css/**", "/js/**");
    }

    @Bean
    public UserRepository userRepository(JpaUserRepository jpaRepo) {
        return new UserRepository() {
            @Override
            public Optional<User> findByUsername(String username) {
                return jpaRepo.findByUsername(username);
            }

            @Override
            public User save(User user) {
                return jpaRepo.save(user);
            }

            @Override
            public Optional<User> findById(Long id) {
                return jpaRepo.findById(id);
            }

            @Override
            public boolean existsByUsername(String username) {
                return jpaRepo.existsByUsername(username);
            }
        };
    }

    @Bean
    public MatchRepository matchRepository(JpaMatchRepository jpaRepo) {
        return new MatchRepository() {
            @Override
            public List<Match> findAll() {
                return jpaRepo.findAll();
            }

            @Override
            public Optional<Match> findById(Long id) {
                return jpaRepo.findById(id);
            }

            @Override
            public Match save(Match match) {
                return jpaRepo.save(match);
            }

            @Override
            public void deleteById(Long id) {
                jpaRepo.deleteById(id);
            }
        };
    }

    @Bean
    public SeatCategoryRepository seatCategoryRepository(JpaSeatCategoryRepository jpaRepo) {
        return new SeatCategoryRepository() {
            @Override
            public List<SeatCategory> findAll() {
                return jpaRepo.findAll();
            }

            @Override
            public Optional<SeatCategory> findById(Long id) {
                return jpaRepo.findById(id);
            }

            @Override
            public SeatCategory save(SeatCategory category) {
                return jpaRepo.save(category);
            }
        };
    }

    @Bean
    public SeatRepository seatRepository(JpaSeatRepository jpaRepo) {
        return new SeatRepository() {
            @Override
            public List<Seat> findAll() {
                return jpaRepo.findAll();
            }

            @Override
            public Optional<Seat> findById(Long id) {
                return jpaRepo.findById(id);
            }

            @Override
            public Seat save(Seat seat) {
                return jpaRepo.save(seat);
            }
        };
    }

    @Bean
    public TicketRepository ticketRepository(JpaTicketRepository jpaRepo) {
        return new TicketRepository() {
            @Override
            public List<Ticket> findAll() {
                return jpaRepo.findAll();
            }

            @Override
            public Optional<Ticket> findById(Long id) {
                return jpaRepo.findById(id);
            }

            @Override
            public Optional<Ticket> findByTicketCode(String ticketCode) {
                return jpaRepo.findByTicketCode(ticketCode);
            }

            @Override
            public List<Ticket> findByUserId(Long userId) {
                return jpaRepo.findByUserId(userId);
            }

            @Override
            public List<Ticket> findByMatchId(Long matchId) {
                return jpaRepo.findByMatchId(matchId);
            }

            @Override
            public Optional<Ticket> findBySeatIdAndMatchId(Long seatId, Long matchId) {
                return jpaRepo.findBySeatIdAndMatchId(seatId, matchId);
            }

            @Override
            public Ticket save(Ticket ticket) {
                return jpaRepo.save(ticket);
            }
        };
    }
}
