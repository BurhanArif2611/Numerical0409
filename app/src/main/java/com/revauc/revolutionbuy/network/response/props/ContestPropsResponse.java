package com.revauc.revolutionbuy.network.response.props;

/**
 * Created by hemant on 24/05/17.
 */

public class ContestPropsResponse {

    private PropsData userContestProps;
    private int userEntryCount;

    public PropsData getUserContestProps() {
        return userContestProps;
    }

    public void setUserContestProps(PropsData userContestProps) {
        this.userContestProps = userContestProps;
    }

    public int getUserEntryCount() {
        return userEntryCount;
    }

    public void setUserEntryCount(int userEntryCount) {
        this.userEntryCount = userEntryCount;
    }
}
