package com.revauc.revolutionbuy.network.response.contest;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ContestPrize implements Parcelable {

    private String prizeId;
    private int position;
    private int numEntrants;
    private int amount;
    private int contestId;
    private int startPositionIndex;
    private int endPositionIndex;

    public String getPrizeId() {
        return prizeId;
    }

    public int getPosition() {
        return position;
    }

    public int getNumEntrants() {
        return numEntrants;
    }

    public int getAmount() {
        return amount;
    }

    public int getContestId() {
        return contestId;
    }

    public int getStartPositionIndex() {
        return startPositionIndex;
    }

    public void setStartPositionIndex(int startPositionIndex) {
        this.startPositionIndex = startPositionIndex;
    }

    public int getEndPositionIndex() {
        return endPositionIndex;
    }

    public void setEndPositionIndex(int endPositionIndex) {
        this.endPositionIndex = endPositionIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.prizeId);
        dest.writeInt(this.position);
        dest.writeInt(this.numEntrants);
        dest.writeInt(this.amount);
        dest.writeInt(this.contestId);
        dest.writeInt(this.startPositionIndex);
        dest.writeInt(this.endPositionIndex);
    }

    public ContestPrize() {
    }

    protected ContestPrize(Parcel in) {
        this.prizeId = in.readString();
        this.position = in.readInt();
        this.numEntrants = in.readInt();
        this.amount = in.readInt();
        this.contestId = in.readInt();
        this.startPositionIndex = in.readInt();
        this.endPositionIndex = in.readInt();
    }

    public static final Parcelable.Creator<ContestPrize> CREATOR = new Parcelable.Creator<ContestPrize>() {
        @Override
        public ContestPrize createFromParcel(Parcel source) {
            return new ContestPrize(source);
        }

        @Override
        public ContestPrize[] newArray(int size) {
            return new ContestPrize[size];
        }
    };
}
