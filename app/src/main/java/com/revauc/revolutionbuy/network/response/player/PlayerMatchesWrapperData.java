package com.revauc.revolutionbuy.network.response.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by hemant on 28/04/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerMatchesWrapperData {

    private String team = null;
    private String opponentTeam = null;
    private String eventDate = null;

    private List<PlayerStat> stats;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getOpponentTeam() {
        return opponentTeam;
    }

    public void setOpponentTeam(String opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public List<PlayerStat> getStats() {
        return stats;
    }

    public void setStats(List<PlayerStat> stats) {
        this.stats = stats;
    }
}
