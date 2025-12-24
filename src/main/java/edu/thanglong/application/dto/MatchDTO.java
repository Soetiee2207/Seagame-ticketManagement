package edu.thanglong.application.dto;

import java.time.LocalDateTime;

public class MatchDTO {
    private Long id;
    private String matchName;
    private LocalDateTime startTime;
    private String location;

    public MatchDTO() {}

    public MatchDTO(Long id, String matchName, LocalDateTime startTime, String location) {
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

    public static MatchDTOBuilder builder() { return new MatchDTOBuilder(); }

    public static class MatchDTOBuilder {
        private Long id;
        private String matchName;
        private LocalDateTime startTime;
        private String location;

        public MatchDTOBuilder id(Long id) { this.id = id; return this; }
        public MatchDTOBuilder matchName(String matchName) { this.matchName = matchName; return this; }
        public MatchDTOBuilder startTime(LocalDateTime startTime) { this.startTime = startTime; return this; }
        public MatchDTOBuilder location(String location) { this.location = location; return this; }

        public MatchDTO build() {
            return new MatchDTO(id, matchName, startTime, location);
        }
    }
}
