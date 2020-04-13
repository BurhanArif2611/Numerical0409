package com.androidboy.directory.futureInvestment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Segment {
   String Segment,position;

    public String getSegment() {
        return Segment;
    }

    public void setSegment(String segment) {
        Segment = segment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
