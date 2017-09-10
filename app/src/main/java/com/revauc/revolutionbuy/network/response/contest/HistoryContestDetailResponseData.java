package com.revauc.revolutionbuy.network.response.contest;

import java.util.ArrayList;

public class HistoryContestDetailResponseData {

    private ContestHistory contestDetails;

    private ArrayList<ContestPrize> contestPrizes;

    public ContestHistory getContestDetails() {
        return contestDetails;
    }

    public ArrayList<ContestPrize> getContestPrizes() {
        return contestPrizes;
    }
}
