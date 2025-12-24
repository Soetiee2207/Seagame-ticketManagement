package edu.thanglong.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "match_name", length = 200)
    private String matchName;
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(length = 100)
    private String location;

    public Match() {}

    public Match(Long id, String matchName, LocalDateTime startTime, String location) {
        this.id = id;
        this.matchName = matchName;
        this.startTime = startTime;
        this.location = location;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMatchName() { return matchName; }
    public void setMatchName(String matchName) { this.matchName = matchName; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public static MatchBuilder builder() { return new MatchBuilder(); }

    public static class MatchBuilder {
        private Long id;
        private String matchName;
        private LocalDateTime startTime;
        private String location;

        public MatchBuilder id(Long id) { this.id = id; return this; }
        public MatchBuilder matchName(String matchName) { this.matchName = matchName; return this; }
        public MatchBuilder startTime(LocalDateTime startTime) { this.startTime = startTime; return this; }
        public MatchBuilder location(String location) { this.location = location; return this; }

        public Match build() {
            return new Match(id, matchName, startTime, location);
        }
    }
}
