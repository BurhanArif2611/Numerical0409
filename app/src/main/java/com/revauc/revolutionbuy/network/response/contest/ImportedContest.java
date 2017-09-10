package com.revauc.revolutionbuy.network.response.contest;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hemant on 28/04/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportedContest extends Contest implements Parcelable {
    private boolean isCheckBoxSelected;

    public boolean isCheckBoxSelected() {
        return isCheckBoxSelected;
    }

    public void setCheckBoxSelected(boolean checkBoxSelected) {
        isCheckBoxSelected = checkBoxSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.isCheckBoxSelected ? (byte) 1 : (byte) 0);
    }

    public ImportedContest() {
    }

    protected ImportedContest(Parcel in) {
        super(in);
        this.isCheckBoxSelected = in.readByte() != 0;
    }

    public static final Creator<ImportedContest> CREATOR = new Creator<ImportedContest>() {
        @Override
        public ImportedContest createFromParcel(Parcel source) {
            return new ImportedContest(source);
        }

        @Override
        public ImportedContest[] newArray(int size) {
            return new ImportedContest[size];
        }
    };
}
