package com.anvesh.iplboard.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Setter
@Entity
public class Team {
    @Id
    private long id;
    private String teamName;
    private String totalMatches;
    private String totalWins;

    public Team(String teamName, String totalMatches) {
        this.teamName = teamName;
        this.totalMatches = totalMatches;
    }
}
