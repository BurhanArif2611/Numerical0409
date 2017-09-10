package com.revauc.revolutionbuy.network.request.player;


public class PlayerStatsRequest {

    private int playerId;
    private String leagueType;


    public PlayerStatsRequest(int playerId, String leagueType) {
        this.playerId = playerId;
        this.leagueType = leagueType;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(String leagueType) {
        this.leagueType = leagueType;
    }
}
