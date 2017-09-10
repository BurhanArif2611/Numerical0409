package com.revauc.revolutionbuy.network.response.contest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveEntrant {

    private int userId;
    private String userName;
    private boolean following;
    private int entryCount;
    private String userPropPicks;
    private int ranking;
    private int userPoints;
    private int propsLeft;
    private int maxPoints;
    private String userContestId;
    private int totalProps;
    private Double prize;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }

    public String getUserPropPicks() {
        return userPropPicks;
    }

    public void setUserPropPicks(String userPropPicks) {
        this.userPropPicks = userPropPicks;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    public int getPropsLeft() {
        return propsLeft;
    }

    public void setPropsLeft(int propsLeft) {
        this.propsLeft = propsLeft;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getTotalProps() {
        return totalProps;
    }

    public void setTotalProps(int totalProps) {
        this.totalProps = totalProps;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public String getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(String userContestId) {
        this.userContestId = userContestId;
    }
}
