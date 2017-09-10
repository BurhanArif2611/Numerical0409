package com.revauc.revolutionbuy.network.request.contest;

import java.util.ArrayList;

/**
 * Created by hemant on 12/05/17.
 */

public class EnterContest {
//    {
//        "adminContestsId": 1,
//            "entryCount": 2,
//            "amountPaid": 20,
//            "propPickList"


    private int adminContestsId;
    private int userContestId;
    private int entryCount;
    private int amountPaid;
    private int maxPoints;
    private String latLongStr;
    private ArrayList<PropsSelected> propPickList;

    public int getAdminContestsId() {
        return adminContestsId;
    }

    public void setAdminContestsId(int adminContestsId) {
        this.adminContestsId = adminContestsId;
    }

    public int getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(int userContestId) {
        this.userContestId = userContestId;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getLatLongStr() {
        return latLongStr;
    }

    public void setLatLongStr(String latLongStr) {
        this.latLongStr = latLongStr;
    }

    public ArrayList<PropsSelected> getPropPickList() {
        return propPickList;
    }

    public void setPropPickList(ArrayList<PropsSelected> propPickList) {
        this.propPickList = propPickList;
    }
}
