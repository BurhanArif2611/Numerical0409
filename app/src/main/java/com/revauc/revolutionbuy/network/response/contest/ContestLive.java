package com.revauc.revolutionbuy.network.response.contest;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContestLive extends RealmObject implements Parcelable {


    private int contestId;
    private String gameImage;
    private Integer entryFee;
    private Integer winnersPoint;
    private Boolean multiEntryAllowed;
    private Integer totalEntries;
    private Integer maxEntriesAllowed;
    private Integer maxEntriesPerUser;
    private Integer totalParticipents;
    private Integer pointsMade;
    private String earnedPrize;
    private Integer totalPrize;
    private String startTime;
    private String endTime;
    private String name;
    private String type;
    private Integer noOfEntrantsPrizePaid;
    private String leagueType;
    private Integer ranking;
    private Boolean featured;

    @PrimaryKey
    private Integer userContestId;
    private Integer adminContestId;
    private Integer pointsAwayFromFirstPostion;
    private Integer pointAwayFromLastPaidPosition;
    private Integer livePoints;
    private String summary;
    private String generalInfo;
    private Boolean belongsToCurrentUser;


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

    public Integer getPointsAwayFromFirstPostion() {
        return pointsAwayFromFirstPostion;
    }

    public void setPointsAwayFromFirstPostion(Integer pointsAwayFromFirstPostion) {
        this.pointsAwayFromFirstPostion = pointsAwayFromFirstPostion;
    }

    public Integer getPointAwayFromLastPaidPosition() {
        return pointAwayFromLastPaidPosition;
    }

    public void setPointAwayFromLastPaidPosition(Integer pointAwayFromLastPaidPosition) {
        this.pointAwayFromLastPaidPosition = pointAwayFromLastPaidPosition;
    }

    public Integer getLivePoints() {
        return livePoints;
    }

    public void setLivePoints(Integer livePoints) {
        this.livePoints = livePoints;
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(Integer totalEntries) {
        this.totalEntries = totalEntries;
    }

    public Integer getMaxEntriesAllowed() {
        return maxEntriesAllowed;
    }

    public void setMaxEntriesAllowed(Integer maxEntriesAllowed) {
        this.maxEntriesAllowed = maxEntriesAllowed;
    }

    public Integer getMaxEntriesPerUser() {
        return maxEntriesPerUser;
    }

    public void setMaxEntriesPerUser(Integer maxEntriesPerUser) {
        this.maxEntriesPerUser = maxEntriesPerUser;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
        dest.writeValue(this.multiEntryAllowed);
        dest.writeValue(this.totalEntries);
        dest.writeValue(this.maxEntriesAllowed);
        dest.writeValue(this.maxEntriesPerUser);
        dest.writeValue(this.totalParticipents);
        dest.writeValue(this.pointsMade);
        dest.writeString(this.earnedPrize);
        dest.writeValue(this.totalPrize);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeValue(this.noOfEntrantsPrizePaid);
        dest.writeString(this.leagueType);
        dest.writeValue(this.ranking);
        dest.writeValue(this.featured);
        dest.writeValue(this.userContestId);
        dest.writeValue(this.adminContestId);
        dest.writeValue(this.pointsAwayFromFirstPostion);
        dest.writeValue(this.pointAwayFromLastPaidPosition);
        dest.writeValue(this.livePoints);
        dest.writeString(this.summary);
        dest.writeString(this.generalInfo);
        dest.writeValue(this.belongsToCurrentUser);
    }

    public ContestLive() {
    }

    protected ContestLive(Parcel in) {
        this.contestId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.gameImage = in.readString();
        this.entryFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.winnersPoint = (Integer) in.readValue(Integer.class.getClassLoader());
        this.multiEntryAllowed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.totalEntries = (Integer) in.readValue(Integer.class.getClassLoader());
        this.maxEntriesAllowed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.maxEntriesPerUser = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalParticipents = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pointsMade = (Integer) in.readValue(Integer.class.getClassLoader());
        this.earnedPrize = in.readString();
        this.totalPrize = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.noOfEntrantsPrizePaid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.leagueType = in.readString();
        this.ranking = (Integer) in.readValue(Integer.class.getClassLoader());
        this.featured = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.userContestId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.adminContestId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pointsAwayFromFirstPostion = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pointAwayFromLastPaidPosition = (Integer) in.readValue(Integer.class.getClassLoader());
        this.livePoints = (Integer) in.readValue(Integer.class.getClassLoader());
        this.summary = in.readString();
        this.generalInfo = in.readString();
        this.belongsToCurrentUser = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<ContestLive> CREATOR = new Creator<ContestLive>() {
        @Override
        public ContestLive createFromParcel(Parcel source) {
            return new ContestLive(source);
        }

        @Override
        public ContestLive[] newArray(int size) {
            return new ContestLive[size];
        }
    };
}
