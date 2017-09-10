package com.revauc.revolutionbuy.network.response.props;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by hemant on 07/05/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Picks implements Parcelable {


    private Integer minPropsToPick;
    private Integer propId;
    private Integer overPoints;
    private Integer underPoints;
    private Double propValue;
    private String contestId;
    private String team1Abbr;
    private String team1locationType;
    private String team2locationType;
    private String team1AbbrPlayer2;
    private String team2AbbrPlayer2;
    private String team1locationTypePlayer2;
    private String team2locationTypePlayer2;
    private int player1TeamId;
    private int player2TeamId;
    private Double player1Advantage;
    private Double player2Advantage;
    private int player1Points;
    private int player2Points;
    private Player player1;
    private Player player2;
    private String team2Abbr;
    private String startTime;
    private String startTimePlayer2;
    private String statEventId;
    private String venueState;
    private boolean propDisabled;
    private int selectedState = 0;
    private int selectedVersusPoints;
    private boolean selectedPlayerVersusOver;



    public boolean isSelectedPlayerVersusOver() {
        return selectedPlayerVersusOver;
    }

    public void setSelectedPlayerVersusOver(boolean selectedPlayerVersusOver) {
        this.selectedPlayerVersusOver = selectedPlayerVersusOver;
    }

    public int getSelectedVersusPoints() {
        return selectedVersusPoints;
    }

    public void setSelectedVersusPoints(int selectedVersusPoints) {
        this.selectedVersusPoints = selectedVersusPoints;
    }

    public Integer getMinPropsToPick() {
        return minPropsToPick;
    }

    public void setMinPropsToPick(Integer minPropsToPick) {
        this.minPropsToPick = minPropsToPick;
    }

    public String getTeam1locationType() {
        return team1locationType;
    }

    public void setTeam1locationType(String team1locationType) {
        this.team1locationType = team1locationType;
    }

    public String getTeam2locationType() {
        return team2locationType;
    }

    public void setTeam2locationType(String team2locationType) {
        this.team2locationType = team2locationType;
    }

    public Double getPlayer1Advantage() {
        return player1Advantage;
    }

    public void setPlayer1Advantage(Double player1Advantage) {
        this.player1Advantage = player1Advantage;
    }

    public Double getPlayer2Advantage() {
        return player2Advantage;
    }

    public void setPlayer2Advantage(Double player2Advantage) {
        this.player2Advantage = player2Advantage;
    }

    public int getPlayer1Points() {
        return player1Points;
    }

    public void setPlayer1Points(int player1Points) {
        this.player1Points = player1Points;
    }

    public int getPlayer2Points() {
        return player2Points;
    }

    public void setPlayer2Points(int player2Points) {
        this.player2Points = player2Points;
    }

    public int getSelectedState() {
        return selectedState;
    }

    public void setSelectedState(int selectedState) {
        this.selectedState = selectedState;
    }

    public int getPlayer1TeamId() {
        return player1TeamId;
    }

    public String getTeam1Abbr() {
        return team1Abbr;
    }

    public void setTeam1Abbr(String team1Abbr) {
        this.team1Abbr = team1Abbr;
    }

    public String getTeam2Abbr() {
        return team2Abbr;
    }

    public void setTeam2Abbr(String team2Abbr) {
        this.team2Abbr = team2Abbr;
    }

    public String getTeam1AbbrPlayer2() {
        return team1AbbrPlayer2;
    }

    public void setTeam1AbbrPlayer2(String team1AbbrPlayer2) {
        this.team1AbbrPlayer2 = team1AbbrPlayer2;
    }

    public String getTeam2AbbrPlayer2() {
        return team2AbbrPlayer2;
    }

    public void setTeam2AbbrPlayer2(String team2AbbrPlayer2) {
        this.team2AbbrPlayer2 = team2AbbrPlayer2;
    }

    public String getTeam1locationTypePlayer2() {
        return team1locationTypePlayer2;
    }

    public void setTeam1locationTypePlayer2(String team1locationTypePlayer2) {
        this.team1locationTypePlayer2 = team1locationTypePlayer2;
    }

    public String getTeam2locationTypePlayer2() {
        return team2locationTypePlayer2;
    }

    public void setTeam2locationTypePlayer2(String team2locationTypePlayer2) {
        this.team2locationTypePlayer2 = team2locationTypePlayer2;
    }

    public void setPlayer1TeamId(int player1TeamId) {
        this.player1TeamId = player1TeamId;
    }

    public int getPlayer2TeamId() {
        return player2TeamId;
    }

    public void setPlayer2TeamId(int player2TeamId) {
        this.player2TeamId = player2TeamId;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }


    public Integer getPropId() {
        return propId;
    }

    public void setPropId(Integer propId) {
        this.propId = propId;
    }

    public Integer getOverPoints() {
        return overPoints;
    }

    public void setOverPoints(Integer overPoints) {
        this.overPoints = overPoints;
    }

    public Integer getUnderPoints() {
        return underPoints;
    }

    public void setUnderPoints(Integer underPoints) {
        this.underPoints = underPoints;
    }

    public Double getPropValue() {
        return propValue;
    }

    public void setPropValue(Double propValue) {
        this.propValue = propValue;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }


    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTimePlayer2() {
        return startTimePlayer2;
    }

    public void setStartTimePlayer2(String startTimePlayer2) {
        this.startTimePlayer2 = startTimePlayer2;
    }

    public String getStatEventId() {
        return statEventId;
    }

    public void setStatEventId(String statEventId) {
        this.statEventId = statEventId;
    }

    public String getVenueState() {
        return venueState;
    }

    public void setVenueState(String venueState) {
        this.venueState = venueState;
    }

    public String getSpeciality() {
        return "PTS+AST+REB";
    }

    public boolean isPropDisabled() {
        return propDisabled;
    }

    public void setPropDisabled(boolean propDisabled) {
        this.propDisabled = propDisabled;
    }

    public Picks() {
    }

    @Override
    public boolean equals(Object obj) {

        boolean same = false;

        if (obj != null && obj instanceof Picks) {
            same = this.propId.equals(((Picks) obj).propId);
            //Objects.equals(this.propId, ((Picks) obj).propId)
        }
        return same;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.minPropsToPick);
        dest.writeValue(this.propId);
        dest.writeValue(this.overPoints);
        dest.writeValue(this.underPoints);
        dest.writeValue(this.propValue);
        dest.writeString(this.contestId);
        dest.writeString(this.team1Abbr);
        dest.writeString(this.team1locationType);
        dest.writeString(this.team2locationType);
        dest.writeString(this.team1AbbrPlayer2);
        dest.writeString(this.team2AbbrPlayer2);
        dest.writeString(this.team1locationTypePlayer2);
        dest.writeString(this.team2locationTypePlayer2);
        dest.writeInt(this.player1TeamId);
        dest.writeInt(this.player2TeamId);
        dest.writeValue(this.player1Advantage);
        dest.writeValue(this.player2Advantage);
        dest.writeInt(this.player1Points);
        dest.writeInt(this.player2Points);
        dest.writeParcelable(this.player1, flags);
        dest.writeParcelable(this.player2, flags);
        dest.writeString(this.team2Abbr);
        dest.writeString(this.startTime);
        dest.writeString(this.startTimePlayer2);
        dest.writeString(this.statEventId);
        dest.writeString(this.venueState);
        dest.writeInt(this.selectedState);
        dest.writeInt(this.selectedVersusPoints);
        dest.writeByte(this.selectedPlayerVersusOver ? (byte) 1 : (byte) 0);
        dest.writeByte(this.propDisabled ? (byte) 1 : (byte) 0);
    }

    protected Picks(Parcel in) {
        this.minPropsToPick = (Integer) in.readValue(Integer.class.getClassLoader());
        this.propId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overPoints = (Integer) in.readValue(Integer.class.getClassLoader());
        this.underPoints = (Integer) in.readValue(Integer.class.getClassLoader());
        this.propValue = (Double) in.readValue(Double.class.getClassLoader());
        this.contestId = in.readString();
        this.team1Abbr = in.readString();
        this.team1locationType = in.readString();
        this.team2locationType = in.readString();
        this.team1AbbrPlayer2 = in.readString();
        this.team2AbbrPlayer2 = in.readString();
        this.team1locationTypePlayer2 = in.readString();
        this.team2locationTypePlayer2 = in.readString();
        this.player1TeamId = in.readInt();
        this.player2TeamId = in.readInt();
        this.player1Advantage = (Double) in.readValue(Double.class.getClassLoader());
        this.player2Advantage = (Double) in.readValue(Double.class.getClassLoader());
        this.player1Points = in.readInt();
        this.player2Points = in.readInt();
        this.player1 = in.readParcelable(Player.class.getClassLoader());
        this.player2 = in.readParcelable(Player.class.getClassLoader());
        this.team2Abbr = in.readString();
        this.startTime = in.readString();
        this.startTimePlayer2 = in.readString();
        this.statEventId = in.readString();
        this.venueState = in.readString();
        this.selectedState = in.readInt();
        this.selectedVersusPoints = in.readInt();
        this.selectedPlayerVersusOver = in.readByte() != 0;
        this.propDisabled = in.readByte() != 0;
    }

    public static final Creator<Picks> CREATOR = new Creator<Picks>() {
        @Override
        public Picks createFromParcel(Parcel source) {
            return new Picks(source);
        }

        @Override
        public Picks[] newArray(int size) {
            return new Picks[size];
        }
    };
}


