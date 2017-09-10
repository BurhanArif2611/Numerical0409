package com.revauc.revolutionbuy.network.response.props;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by hemant on 18/05/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPick {

    public int propId;
    public boolean isICE;
    public boolean isOver;
    public boolean icePrimary;
    public int userPoints;
    public int entryCount;


}
