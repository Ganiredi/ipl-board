package com.anvesh.iplboard.controllers;


import com.anvesh.iplboard.model.Team;
import com.anvesh.iplboard.repositories.TeamRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/team/")
public class TeamController {

    public TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("{teamName}")
    public Team getTeamData(@PathVariable String teamName) {
        return this.teamRepository.findByTeamName(teamName);
    }

}
