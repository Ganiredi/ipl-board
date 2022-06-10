package com.anvesh.iplboard.repositories;

import com.anvesh.iplboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    public List<Match> findByTeam1OrTeam2OrderByDateDesc(String teams1, String team2, Pageable pageable);

    default List<Match> findByTeamNames(String teamName, int count) {
        return findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
}
