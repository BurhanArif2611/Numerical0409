package com.revauc.revolutionbuy.network.response.contest;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.realm.RealmObject;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContestHistory implements Parcelable {

    private int contestId;
    private String gameImage;
    private Integer entryFee;
    private Integer winnersPoint;
    private Integer totalEntries;
    private Boolean multiEntryAllowed;
    private Integer totalParticipents;
    private Integer pointsMade;
    private String earnedPrize;
    private Integer totalPrize;
    private String startTime;
    private String name;
    private String type;
    private Integer noOfEntrantsPrizePaid;
    private Integer guaranteedPrizeMoney;
    private String leagueType;
    private Integer ranking;
    private Boolean featured;
    private Integer userContestId;
    private Integer adminContestId;
    private Integer firstWinPosition;
    private Integer lastWinPosition;
    private Integer livePoints;
    private String summary;
    private String generalInfo;
    private Boolean belongsToCurrentUser;
    private Integer positionAwayFromLastWinningPosition;


    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }

    public Integer getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Integer entryFee) {
        this.entryFee = entryFee;
    }

    public Integer getWinnersPoint() {
        return winnersPoint;
    }

    public void setWinnersPoint(Integer winnersPoint) {
        this.winnersPoint = winnersPoint;
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

    public Integer getTotalParticipents() {
        return totalParticipents;
    }

    public void setTotalParticipents(Integer totalParticipents) {
        this.totalParticipents = totalParticipents;
    }

    public Integer getPointsMade() {
        return pointsMade;
    }

    public void setPointsMade(Integer pointsMade) {
        this.pointsMade = pointsMade;
    }

    public String getEarnedPrize() {
        return earnedPrize;
    }

    public void setEarnedPrize(String earnedPrize) {
        this.earnedPrize = earnedPrize;
    }

    public Integer getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(Integer totalPrize) {
        this.totalPrize = totalPrize;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNoOfEntrantsPrizePaid() {
        return noOfEntrantsPrizePaid;
    }

    public void setNoOfEntrantsPrizePaid(Integer noOfEntrantsPrizePaid) {
        this.noOfEntrantsPrizePaid = noOfEntrantsPrizePaid;
    }

    public Integer getGuaranteedPrizeMoney() {
        return guaranteedPrizeMoney;
    }

    public void setGuaranteedPrizeMoney(Integer guaranteedPrizeMoney) {
        this.guaranteedPrizeMoney = guaranteedPrizeMoney;
    }

    public String getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(String leagueType) {
        this.leagueType = leagueType;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Integer getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(Integer userContestId) {
        this.userContestId = userContestId;
    }

    public Integer getAdminContestId() {
        return adminContestId;
    }

    public void setAdminContestId(Integer adminContestId) {
        this.adminContestId = adminContestId;
    }

    public Integer getFirstWinPosition() {
        return firstWinPosition;
    }

    public void setFirstWinPosition(Integer firstWinPosition) {
        this.firstWinPosition = firstWinPosition;
    }

    public Integer getLastWinPosition() {
        return lastWinPosition;
    }

    public void setLastWinPosition(Integer lastWinPosition) {
        this.lastWinPosition = lastWinPosition;
    }

    public Integer getLivePoints() {
        return livePoints;
    }

    public void setLivePoints(Integer livePoints) {
        this.livePoints = livePoints;
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

    public Boolean getBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }

    public void setBelongsToCurrentUser(Boolean belongsToCurrentUser) {
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public Integer getPositionAwayFromLastWinningPosition() {
        return positionAwayFromLastWinningPosition;
    }

    public void setPositionAwayFromLastWinningPosition(Integer positionAwayFromLastWinningPosition) {
        this.positionAwayFromLastWinningPosition = positionAwayFromLastWinningPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.contestId);
        dest.writeString(this.gameImage);
        dest.writeValue(this.entryFee);
        dest.writeValue(this.winnersPoint);
        dest.writeValue(this.totalEntries);
        dest.writeValue(this.multiEntryAllowed);
        dest.writeValue(this.totalParticipents);
        dest.writeValue(this.pointsMade);
        dest.writeString(this.earnedPrize);
        dest.writeValue(this.totalPrize);
        dest.writeString(this.startTime);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeValue(this.noOfEntrantsPrizePaid);
        dest.writeValue(this.guaranteedPrizeMoney);
        dest.writeString(this.leagueType);
        dest.writeValue(this.ranking);
        dest.writeValue(this.featured);
        dest.writeValue(this.userContestId);
        dest.writeValue(this.adminContestId);
        dest.writeValue(this.firstWinPosition);
        dest.writeValue(this.lastWinPosition);
        dest.writeValue(this.livePoints);
        dest.writeString(this.summary);
        dest.writeString(this.generalInfo);
        dest.writeValue(this.belongsToCurrentUser);
        dest.writeValue(this.positionAwayFromLastWinningPosition);
    }

    public ContestHistory() {
    }

    protected ContestHistory(Parcel in) {
        this.contestId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.gameImage = in.readString();
        this.entryFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.winnersPoint = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalEntries = (Integer) in.readValue(Integer.class.getClassLoader());
        this.multiEntryAllowed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.totalParticipents = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pointsMade = (Integer) in.readValue(Integer.class.getClassLoader());
        this.earnedPrize = in.readString();
        this.totalPrize = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startTime = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.noOfEntrantsPrizePaid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.guaranteedPrizeMoney = (Integer) in.readValue(Integer.class.getClassLoader());
        this.leagueType = in.readString();
        this.ranking = (Integer) in.readValue(Integer.class.getClassLoader());
        this.featured = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.userContestId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.adminContestId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.firstWinPosition = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastWinPosition = (Integer) in.readValue(Integer.class.getClassLoader());
        this.livePoints = (Integer) in.readValue(Integer.class.getClassLoader());
        this.summary = in.readString();
        this.generalInfo = in.readString();
        this.belongsToCurrentUser = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.positionAwayFromLastWinningPosition = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<ContestHistory> CREATOR = new Parcelable.Creator<ContestHistory>() {
        @Override
        public ContestHistory createFromParcel(Parcel source) {
            return new ContestHistory(source);
        }

        @Override
        public ContestHistory[] newArray(int size) {
            return new ContestHistory[size];
        }
    };
}
