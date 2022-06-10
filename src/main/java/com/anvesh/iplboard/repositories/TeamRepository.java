package com.anvesh.iplboard.repositories;

import com.anvesh.iplboard.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

    public Team findByTeamName(String teamName);
}
