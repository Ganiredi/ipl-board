package com.anvesh.iplboard.controllers;


import com.anvesh.iplboard.model.Team;
import com.anvesh.iplboard.repositories.MatchRepository;
import com.anvesh.iplboard.repositories.TeamRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TeamController {

    public TeamRepository teamRepository;
    public MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/team/{teamName}")
    public Team getTeamData(@PathVariable String teamName) {
        Team team = this.teamRepository.findByTeamName(teamName);
        team.setMatches(matchRepository.findByTeamNames(teamName, 4));
        return team;
    }

}
