package com.revauc.revolutionbuy.network.response.props;

import com.revauc.revolutionbuy.util.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by hemant on 18/05/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Props {

    private Picks contestProp;
    private UserPick userPropPick;
    private int itemType;
    private boolean isFreezed;

    public Picks getContestProp() {
        return contestProp;
    }

    public UserPick getUserPropPick() {
        return userPropPick;
    }

    public void setContestProp(Picks contestProp) {
        this.contestProp = contestProp;
    }

    public void setUserPropPick(UserPick userPropPick) {
        this.userPropPick = userPropPick;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public boolean isFreezed() {
        return isFreezed;
    }

    public void setFreezed(boolean freezed) {
        isFreezed = freezed;
    }

    @Override
    public boolean equals(Object obj) {

        boolean same = false;
        if (obj != null && obj instanceof Props) {
            same = this.contestProp.getPropId().equals(((Props) obj).contestProp.getPropId());
            //Objects.equals(this.propId, ((Picks) obj).propId)
        }
        return same;

    }
}
