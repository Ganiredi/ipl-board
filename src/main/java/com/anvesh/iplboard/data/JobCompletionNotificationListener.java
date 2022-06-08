package com.anvesh.iplboard.data;


import com.anvesh.iplboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);


    private final EntityManager entityManagerl;

    @Autowired

    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManagerl = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

           /* jdbcTemplate.query("SELECT team1, team2 FROM match ",
                    (rs, row) -> "team 1 :" +
                            rs.getString(1) + "team 1 :" +
                            rs.getString(2)
            ).forEach(System.out::println);*/

            Map<String, Team> teamData = new HashMap<>();

            entityManagerl.createQuery("select distinct m.team1, count(*) from Match m group by m.team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(team -> new Team((String) team[0], (long) team[1]))
                    .forEach(team -> teamData.put(team.getTeamName(), team));

            entityManagerl.createQuery("select distinct m.team2, count(*) from Match m group by m.team2 ", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        if (team != null) {
                            team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
                        }
                    });

            entityManagerl.createQuery("select m.matchWinner ,count (*) from Match  m group by m.matchWinner", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        if (team != null) {
                            team.setTotalWins((long) e[1]);
                        }
                    });

            teamData.values().forEach(entityManagerl::persist);
            teamData.values().forEach(System.out::println);

        }
    }
}