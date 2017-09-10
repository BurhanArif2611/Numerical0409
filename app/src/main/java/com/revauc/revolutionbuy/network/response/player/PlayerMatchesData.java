package com.revauc.revolutionbuy.network.response.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by hemant on 28/04/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerMatchesData {

    private String name = null;
    private List<PlayerMatchesWrapperData> playerStatsWrapper;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerMatchesWrapperData> getPlayerStatsWrapper() {
        return playerStatsWrapper;
    }

    public void setPlayerStatsWrapper(List<PlayerMatchesWrapperData> playerStatsWrapper) {
        this.playerStatsWrapper = playerStatsWrapper;
    }
}
