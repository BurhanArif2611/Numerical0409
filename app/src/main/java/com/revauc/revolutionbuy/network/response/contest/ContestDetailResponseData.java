package com.revauc.revolutionbuy.network.response.contest;

import java.util.ArrayList;

public class ContestDetailResponseData {

    private Contest contestDetails;

    private ArrayList<ContestPrize> contestPrizes;

    public Contest getContestDetails() {
        return contestDetails;
    }

    public ArrayList<ContestPrize> getContestPrizes() {
        return contestPrizes;
    }
}
