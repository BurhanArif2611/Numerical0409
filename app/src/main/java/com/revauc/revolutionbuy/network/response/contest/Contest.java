package com.revauc.revolutionbuy.network.response.contest;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by hemant on 28/04/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contest implements Parcelable {

    //    "contestId":"26",
//            "name":"con11",
//            "summary":"test summary 11",
//            "generalInfo":"testdfsdfsdfdsfdsfsdfdsfdsfsdfdsfdsfcxxc. dfsdfdsc dfdsc",
//            "startTime":"2017-05-17 03:22",
//            "endTime":"2017-05-17 04:22",
//            "type":"VERSUS",
//            "leagueType":"NFL",
//            "entryFee":20.00,
//            "maxEntriesAllowed":110,
//            "totalEntries":50,
//            "multiEntryAllowed":false,
//            "maxEntriesPerUser":3,
//            "guaranteedPrizeMoney":2000.00,
//            "propsAvailable":0,
//            "minPropsToPick":10,
//            "featured":false,
//            "live":false,
//            "timeToStart":491548214,
//            "belongsToCurrentUser":false,
//            "events":[
//            ],
//            "gameImage":"https://s3.amazonaws.com/s3-p2f-dev/images/football.png",
//            "users":[
//            ]

    private String contestId;
    private String userContestId;
    private String name;
    private String summary;
    private String generalInfo;
    private String startTime;
    private String endTime;
    private String contestStartTimeUTC;
    private String type;
    private String leagueType;
    private String gameImage;
    private String contestStatus;
    private Integer entryFee;
    private Integer maxEntriesAllowed;
    private Integer totalEntries;
    private Boolean multiEntryAllowed;

    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }


    private Integer maxEntriesPerUser;
    private Integer guaranteedPrizeMoney;
    private Integer firstPrizePayout;
    private Integer propsAvailable;
    private Integer minPropsToPick;
    private Boolean featured;
    private Boolean live;
    private Boolean contestOver;
    private long timeToStart;
    private Boolean belongsToCurrentUser;
    private List<Object> events = null;
    private List<Object> users = null;

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(String userContestId) {
        this.userContestId = userContestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContestStartTimeUTC() {
        return contestStartTimeUTC;
    }

    public void setContestStartTimeUTC(String contestStartTimeUTC) {
        this.contestStartTimeUTC = contestStartTimeUTC;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(String leagueType) {
        this.leagueType = leagueType;
    }

    public String getContestStatus() {
        return contestStatus;
    }

    public void setContestStatus(String contestStatus) {
        this.contestStatus = contestStatus;
    }

    public Integer getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Integer entryFee) {
        this.entryFee = entryFee;
    }

    public Integer getMaxEntriesAllowed() {
        return maxEntriesAllowed;
    }

    public void setMaxEntriesAllowed(Integer maxEntriesAllowed) {
        this.maxEntriesAllowed = maxEntriesAllowed;
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(Integer totalEntries) {
        this.totalEntries = totalEntries;
    }

    public Boolean getMultiEntryAllowed() {
        return multiEntryAllowed;
    }

    public void setMultiEntryAllowed(Boolean multiEntryAllowed) {
        this.multiEntryAllowed = multiEntryAllowed;
    }

    public Integer getMaxEntriesPerUser() {
        return maxEntriesPerUser;
    }

    public void setMaxEntriesPerUser(Integer maxEntriesPerUser) {
        this.maxEntriesPerUser = maxEntriesPerUser;
    }

    public Integer getGuaranteedPrizeMoney() {
        return guaranteedPrizeMoney;
    }

    public Integer getFirstPrizePayout() {
        return firstPrizePayout;
    }

    public void setFirstPrizePayout(Integer firstPrizePayout) {
        this.firstPrizePayout = firstPrizePayout;
    }

    public void setGuaranteedPrizeMoney(Integer guaranteedPrizeMoney) {
        this.guaranteedPrizeMoney = guaranteedPrizeMoney;
    }

    public Integer getPropsAvailable() {
        return propsAvailable;
    }

    public void setPropsAvailable(Integer propsAvailable) {
        this.propsAvailable = propsAvailable;
    }

    public Integer getMinPropsToPick() {
        return minPropsToPick;
    }

    public void setMinPropsToPick(Integer minPropsToPick) {
        this.minPropsToPick = minPropsToPick;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public Boolean getContestOver() {
        return contestOver;
    }

    public void setContestOver(Boolean contestOver) {
        this.contestOver = contestOver;
    }

    public long getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(long timeToStart) {
        this.timeToStart = timeToStart;
    }

    public Boolean getBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }

    public void setBelongsToCurrentUser(Boolean belongsToCurrentUser) {
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public List<Object> getEvents() {
        return events;
    }

    public void setEvents(List<Object> events) {
        this.events = events;
    }

    public List<Object> getUsers() {
        return users;
    }

    public void setUsers(List<Object> users) {
        this.users = users;
    }


    public Contest() {
    }


    @Override
    public boolean equals(Object obj) {
        boolean same = false;

        if (obj != null && obj instanceof Contest) {
            same = this.contestId.equals(((Contest) obj).getContestId());
            //Objects.equals(this.propId, ((Picks) obj).propId)
        }
        return same;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contestId);
        dest.writeString(this.userContestId);
        dest.writeString(this.name);
        dest.writeString(this.summary);
        dest.writeString(this.generalInfo);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.contestStartTimeUTC);
        dest.writeString(this.type);
        dest.writeString(this.leagueType);
        dest.writeString(this.gameImage);
        dest.writeString(this.contestStatus);
        dest.writeValue(this.entryFee);
        dest.writeValue(this.maxEntriesAllowed);
        dest.writeValue(this.totalEntries);
        dest.writeValue(this.multiEntryAllowed);
        dest.writeValue(this.maxEntriesPerUser);
        dest.writeValue(this.guaranteedPrizeMoney);
        dest.writeValue(this.firstPrizePayout);
        dest.writeValue(this.propsAvailable);
        dest.writeValue(this.minPropsToPick);
        dest.writeValue(this.featured);
        dest.writeValue(this.live);
        dest.writeValue(this.contestOver);
        dest.writeLong(this.timeToStart);
        dest.writeValue(this.belongsToCurrentUser);
        dest.writeList(this.events);
        dest.writeList(this.users);
    }

    protected Contest(Parcel in) {
        this.contestId = in.readString();
        this.userContestId = in.readString();
        this.name = in.readString();
        this.summary = in.readString();
        this.generalInfo = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.contestStartTimeUTC = in.readString();
        this.type = in.readString();
        this.leagueType = in.readString();
        this.gameImage = in.readString();
        this.contestStatus = in.readString();
        this.entryFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.maxEntriesAllowed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalEntries = (Integer) in.readValue(Integer.class.getClassLoader());
        this.multiEntryAllowed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.maxEntriesPerUser = (Integer) in.readValue(Integer.class.getClassLoader());
        this.guaranteedPrizeMoney = (Integer) in.readValue(Integer.class.getClassLoader());
        this.firstPrizePayout = (Integer) in.readValue(Integer.class.getClassLoader());
        this.propsAvailable = (Integer) in.readValue(Integer.class.getClassLoader());
        this.minPropsToPick = (Integer) in.readValue(Integer.class.getClassLoader());
        this.featured = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.live = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.contestOver = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.timeToStart = in.readLong();
        this.belongsToCurrentUser = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.events = new ArrayList<Object>();
        in.readList(this.events, Object.class.getClassLoader());
        this.users = new ArrayList<Object>();
        in.readList(this.users, Object.class.getClassLoader());
    }

    public static final Creator<Contest> CREATOR = new Creator<Contest>() {
        @Override
        public Contest createFromParcel(Parcel source) {
            return new Contest(source);
        }

        @Override
        public Contest[] newArray(int size) {
            return new Contest[size];
        }
    };
}
