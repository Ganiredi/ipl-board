package com.anvesh.iplboard.repositories;

import com.anvesh.iplboard.model.Match;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    public List<Match> findByTeam1OrTeam2OrderByDateDesc(String teams1, String team2);
}
