package com.revauc.revolutionbuy.network.response.contest;

import java.util.ArrayList;

public class LiveContestDetailResponseData {

    private ContestLive contestDetails;

    private ArrayList<ContestPrize> contestPrizes;

    public ContestLive getContestDetails() {
        return contestDetails;
    }

    public ArrayList<ContestPrize> getContestPrizes() {
        return contestPrizes;
    }
}
