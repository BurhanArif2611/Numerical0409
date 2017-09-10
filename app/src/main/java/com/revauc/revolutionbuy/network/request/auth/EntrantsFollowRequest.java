package com.revauc.revolutionbuy.network.request.auth;

/**
 * Created by hemant on 05/05/17.
 */

public class EntrantsFollowRequest {

    private String userId;
    private boolean isFollowing;

    public EntrantsFollowRequest(String contestId, boolean isFollowing) {
        this.userId = contestId;
        this.isFollowing = isFollowing;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

}
