package com.revauc.revolutionbuy.event;

import com.revauc.revolutionbuy.util.Constants;

/**
 * Created by hemant on 26/04/17.
 */

public class NavigationSelection {

    @Constants.NavigationMode
    private int navigationMode;

    public NavigationSelection(@Constants.NavigationMode int navigationMode, @Constants.LeagueType int leagueType) {
        this.navigationMode = navigationMode;
        this.leagueType = leagueType;
    }

    @Constants.LeagueType
    private int leagueType;

    @Constants.NavigationMode
    public int getNavigationMode() {
        return navigationMode;
    }

    @Constants.LeagueType
    public int getLeagueType() {
        return leagueType;
    }

}
