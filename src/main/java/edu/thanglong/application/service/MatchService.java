package edu.thanglong.application.service;

import edu.thanglong.application.dto.MatchDTO;
import edu.thanglong.domain.entity.Match;
import edu.thanglong.domain.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<MatchDTO> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MatchDTO> getMatchById(Long id) {
        return matchRepository.findById(id).map(this::convertToDTO);
    }

    public Match getMatchEntityById(Long id) {
        return matchRepository.findById(id).orElse(null);
    }

    public MatchDTO createMatch(MatchDTO dto) {
        Match match = Match.builder()
                .matchName(dto.getMatchName())
                .startTime(dto.getStartTime())
                .location(dto.getLocation())
                .build();
        Match saved = matchRepository.save(match);
        return convertToDTO(saved);
    }

    private MatchDTO convertToDTO(Match match) {
        return MatchDTO.builder()
                .id(match.getId())
                .matchName(match.getMatchName())
                .startTime(match.getStartTime())
                .location(match.getLocation())
                .build();
    }
}
