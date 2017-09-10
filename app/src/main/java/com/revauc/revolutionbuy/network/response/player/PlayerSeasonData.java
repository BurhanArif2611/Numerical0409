package com.revauc.revolutionbuy.network.response.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by hemant on 28/04/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerSeasonData {

    private String name = null;
    private int totalGame;

    private List<PlayerStat> playerStats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalGame() {
        return totalGame;
    }

    public void setTotalGame(int totalGame) {
        this.totalGame = totalGame;
    }

    public List<PlayerStat> getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(List<PlayerStat> playerStats) {
        this.playerStats = playerStats;
    }
}
