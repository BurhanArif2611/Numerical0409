package com.revauc.revolutionbuy.network.response.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private long userId;
    private String userName;
    private String email;
    private String role;
    private long walletBal;
    private boolean notificationFlagStartOfContest;
    private boolean notificationFlagPropPicked;
    private String noOfContestTillDateParticipated;
    private String noOfContestsParticipated;
    private String totalWonPrizeMoney;


    public boolean isNotificationFlagStartOfContest() {
        return notificationFlagStartOfContest;
    }

    public void setNotificationFlagStartOfContest(boolean notificationFlagStartOfContest) {
        this.notificationFlagStartOfContest = notificationFlagStartOfContest;
    }

    public boolean isNotificationFlagPropPicked() {
        return notificationFlagPropPicked;
    }

    public void setNotificationFlagPropPicked(boolean notificationFlagPropPicked) {
        this.notificationFlagPropPicked = notificationFlagPropPicked;
    }

    public String getNoOfContestTillDateParticipated() {
        return noOfContestTillDateParticipated;
    }

    public void setNoOfContestTillDateParticipated(String noOfContestTillDateParticipated) {
        this.noOfContestTillDateParticipated = noOfContestTillDateParticipated;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public long getWalletBal() {
        return walletBal;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWalletBal(long walletBal) {
        this.walletBal = walletBal;
    }

    public String getNoOfContestsParticipated() {
        return noOfContestsParticipated;
    }

    public void setNoOfContestsParticipated(String noOfContestsParticipated) {
        this.noOfContestsParticipated = noOfContestsParticipated;
    }

    public String getTotalWonPrizeMoney() {
        return totalWonPrizeMoney;
    }

    public void setTotalWonPrizeMoney(String totalWonPrizeMoney) {
        this.totalWonPrizeMoney = totalWonPrizeMoney;
    }
}
