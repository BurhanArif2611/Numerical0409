package com.revauc.revolutionbuy.network.response.contest;

import com.revauc.revolutionbuy.network.response.props.Player;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by hemant on 22/06/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LiveProps {
    @JsonProperty("propId")
    private Integer propId;
    @JsonProperty("isICE")
    private Boolean isICE;
    @JsonProperty("isOver")
    private Boolean isOver;
    @JsonProperty("event1Complete")
    private Boolean event1Complete;
    @JsonProperty("event2Complete")
    private Boolean event2Complete;
    @JsonProperty("propDisabled;")
    private Boolean propDisabled;
    @JsonProperty("userPoints")
    private Integer userPoints;
    @JsonProperty("entryCount")
    private Integer entryCount;
    @JsonProperty("player1Name")
    private String player1Name;
    @JsonProperty("player2Name")
    private String player2Name;
    @JsonProperty("player1Image")
    private String player1Image;
    @JsonProperty("player2Image")
    private String player2Image;
    @JsonProperty("propValue")
    private Double propValue;
    @JsonProperty("adminPlayerId1")
    private String adminPlayerId1;
    @JsonProperty("adminPlayerId2")
    private String adminPlayerId2;
    @JsonProperty("overPoints")
    private Integer overPoints;
    @JsonProperty("player1Points")
    private int player1Points;
    @JsonProperty("player2Points")
    private int player2Points;

    @JsonProperty("player1Advantage")
    private Double player1Advantage;
    @JsonProperty("player2Advantage")
    private String player2Advantage;
    @JsonProperty("underPoints")
    private Integer underPoints;
    @JsonProperty("ownedPercentage")
    private Double ownedPercentage;
    @JsonProperty("livePropPointsPlayer2")
    private Integer livePropPointsPlayer2;
    @JsonProperty("player1")
    private Player player1;
    @JsonProperty("player2")
    private Player player2;
    @JsonProperty("inningEvent")
    private String inningEvent;
    @JsonProperty("inningEvent2")
    private String inningEvent2;
    @JsonProperty("team1Abbr")
    private String team1Abbr;
    @JsonProperty("team2Abbr")
    private String team2Abbr;
    @JsonProperty("team1Score")
    private String team1Score;
    @JsonProperty("team2Score")
    private String team2Score;
    @JsonProperty("team1AbbrEvent2")
    private String team1AbbrEvent2;
    @JsonProperty("team2AbbrEvent2")
    private String team2AbbrEvent2;
    @JsonProperty("team1ScoreEvent2")
    private String team1ScoreEvent2;
    @JsonProperty("team2ScoreEvent2")
    private String team2ScoreEvent2;
    @JsonProperty("startTimeEvent1")
    private long startTimeEvent1;
    @JsonProperty("player1TeamAbrivation")
    private String player1TeamAbrivation;
    @JsonProperty("player2TeamAbrivation")
    private String player2TeamAbrivation;
    @JsonProperty("player1PositionAbbrevation")
    private String player1PositionAbbrevation;
    @JsonProperty("player2PositionAbbrevation")
    private String player2PositionAbbrevation;
    @JsonProperty("startTimeEvent2")
    private long startTimeEvent2;
    @JsonProperty("team1locationType")
    private String team1locationType;
    @JsonProperty("team2locationType")
    private String team2locationType;
    @JsonProperty("team1locationTypePlayer2")
    private String team1locationTypePlayer2;
    @JsonProperty("team2locationTypePlayer2")
    private String team2locationTypePlayer2;



    public String getPlayer1TeamAbrivation() {
        return player1TeamAbrivation;
    }

    public void setPlayer1TeamAbrivation(String player1TeamAbrivation) {
        this.player1TeamAbrivation = player1TeamAbrivation;
    }

    public String getPlayer2TeamAbrivation() {
        return player2TeamAbrivation;
    }

    public void setPlayer2TeamAbrivation(String player2TeamAbrivation) {
        this.player2TeamAbrivation = player2TeamAbrivation;
    }

    public String getPlayer1PositionAbbrevation() {
        return player1PositionAbbrevation;
    }

    public void setPlayer1PositionAbbrevation(String player1PositionAbbrevation) {
        this.player1PositionAbbrevation = player1PositionAbbrevation;
    }

    public String getPlayer2PositionAbbrevation() {
        return player2PositionAbbrevation;
    }

    public void setPlayer2PositionAbbrevation(String player2PositionAbbrevation) {
        this.player2PositionAbbrevation = player2PositionAbbrevation;
    }

    public Boolean getICE() {
        return isICE;
    }

    public void setICE(Boolean ICE) {
        isICE = ICE;
    }

    public Integer getLivePropPointsPlayer2() {
        return livePropPointsPlayer2;
    }

    public void setLivePropPointsPlayer2(Integer livePropPointsPlayer2) {
        this.livePropPointsPlayer2 = livePropPointsPlayer2;
    }

    @JsonProperty("propDisabled")
    public Boolean getPropDisabled() {
        return propDisabled;
    }

    @JsonProperty("propDisabled")
    public void setPropDisabled(Boolean propDisabled) {
        this.propDisabled = propDisabled;
    }

    @JsonProperty("livePropPoints")
    private Integer livePropPoints;
    @JsonProperty("propParameters")
    private List<PropParameter> propParameters = null;
    @JsonProperty("ice")
    private Boolean ice;
    @JsonProperty("over")
    private Boolean over;
    @JsonProperty("icePrimary")
    private Boolean icePrimary;

    @JsonProperty("propId")
    public Integer getPropId() {
        return propId;
    }

    @JsonProperty("propId")
    public void setPropId(Integer propId) {
        this.propId = propId;
    }

    @JsonProperty("isICE")
    public Boolean getIsICE() {
        return isICE;
    }

    @JsonProperty("isICE")
    public void setIsICE(Boolean isICE) {
        this.isICE = isICE;
    }

    @JsonProperty("isOver")
    public Boolean getIsOver() {
        return isOver;
    }

    @JsonProperty("isOver")
    public void setIsOver(Boolean isOver) {
        this.isOver = isOver;
    }

    @JsonProperty("event1Complete")
    public Boolean getEvent1Complete() {
        return event1Complete;
    }

    @JsonProperty("event1Complete")
    public void setEvent1Complete(Boolean event1Complete) {
        this.event1Complete = event1Complete;
    }

    @JsonProperty("event2Complete")
    public Boolean getEvent2Complete() {
        return event2Complete;
    }

    @JsonProperty("event2Complete")
    public void setEvent2Complete(Boolean event2Complete) {
        this.event2Complete = event2Complete;
    }

    @JsonProperty("userPoints")
    public Integer getUserPoints() {
        return userPoints;
    }

    @JsonProperty("userPoints")
    public void setUserPoints(Integer userPoints) {
        this.userPoints = userPoints;
    }

    @JsonProperty("entryCount")
    public Integer getEntryCount() {
        return entryCount;
    }

    @JsonProperty("entryCount")
    public void setEntryCount(Integer entryCount) {
        this.entryCount = entryCount;
    }

    @JsonProperty("player1Name")
    public String getPlayer1Name() {
        return player1Name;
    }

    @JsonProperty("player1Name")
    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    @JsonProperty("player2Name")
    public String getPlayer2Name() {
        return player2Name;
    }

    @JsonProperty("player2Name")
    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    @JsonProperty("player1Image")
    public String getPlayer1Image() {
        return player1Image;
    }

    @JsonProperty("player1Image")
    public void setPlayer1Image(String player1Image) {
        this.player1Image = player1Image;
    }

    @JsonProperty("player2Image")
    public String getPlayer2Image() {
        return player2Image;
    }

    @JsonProperty("player2Image")
    public void setPlayer2Image(String player2Image) {
        this.player2Image = player2Image;
    }

    @JsonProperty("propValue")
    public Double getPropValue() {
        return propValue;
    }

    @JsonProperty("propValue")
    public void setPropValue(Double propValue) {
        this.propValue = propValue;
    }

    @JsonProperty("adminPlayerId1")
    public String getAdminPlayerId1() {
        return adminPlayerId1;
    }

    @JsonProperty("adminPlayerId1")
    public void setAdminPlayerId1(String adminPlayerId1) {
        this.adminPlayerId1 = adminPlayerId1;
    }

    @JsonProperty("adminPlayerId2")
    public String getAdminPlayerId2() {
        return adminPlayerId2;
    }

    @JsonProperty("adminPlayerId2")
    public void setAdminPlayerId2(String adminPlayerId2) {
        this.adminPlayerId2 = adminPlayerId2;
    }

    @JsonProperty("overPoints")
    public Integer getOverPoints() {
        return overPoints;
    }

    @JsonProperty("overPoints")
    public void setOverPoints(Integer overPoints) {
        this.overPoints = overPoints;
    }

    @JsonProperty("player1Points")
    public int getPlayer1Points() {
        return player1Points;
    }

    @JsonProperty("player1Points")
    public void setPlayer1Points(int player1Points) {
        this.player1Points = player1Points;
    }

    @JsonProperty("player2Points")
    public int getPlayer2Points() {
        return player2Points;
    }

    @JsonProperty("player2Points")
    public void setPlayer2Points(int player2Points) {
        this.player2Points = player2Points;
    }

    @JsonProperty("player1Advantage")
    public Double getPlayer1Advantage() {
        return player1Advantage;
    }

    @JsonProperty("player1Advantage")
    public void setPlayer1Advantage(Double player1Advantage) {
        this.player1Advantage = player1Advantage;
    }

    @JsonProperty("player2Advantage")
    public String getPlayer2Advantage() {
        return player2Advantage;
    }

    @JsonProperty("player2Advantage")
    public void setPlayer2Advantage(String player2Advantage) {
        this.player2Advantage = player2Advantage;
    }

    @JsonProperty("underPoints")
    public Integer getUnderPoints() {
        return underPoints;
    }

    @JsonProperty("underPoints")
    public void setUnderPoints(Integer underPoints) {
        this.underPoints = underPoints;
    }

    @JsonProperty("ownedPercentage")
    public Double getOwnedPercentage() {
        return ownedPercentage;
    }

    @JsonProperty("ownedPercentage")
    public void setOwnedPercentage(Double ownedPercentage) {
        this.ownedPercentage = ownedPercentage;
    }

    @JsonProperty("livePropPoints")
    public Integer getLivePropPoints() {
        return livePropPoints;
    }

    @JsonProperty("livePropPoints")
    public void setLivePropPoints(Integer livePropPoints) {
        this.livePropPoints = livePropPoints;
    }

    @JsonProperty("propParameters")
    public List<PropParameter> getPropParameters() {
        return propParameters;
    }

    @JsonProperty("propParameters")
    public void setPropParameters(List<PropParameter> propParameters) {
        this.propParameters = propParameters;
    }

    @JsonProperty("ice")
    public Boolean getIce() {
        return ice;
    }

    @JsonProperty("ice")
    public void setIce(Boolean ice) {
        this.ice = ice;
    }

    @JsonProperty("over")
    public Boolean getOver() {
        return over;
    }

    @JsonProperty("over")
    public void setOver(Boolean over) {
        this.over = over;
    }

    @JsonProperty("icePrimary")
    public Boolean getIcePrimary() {
        return icePrimary;
    }

    @JsonProperty("icePrimary")
    public void setIcePrimary(Boolean icePrimary) {
        this.icePrimary = icePrimary;
    }

    @JsonProperty("player1")
    public Player getPlayer1() {
        return player1;
    }

    @JsonProperty("player1")
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    @JsonProperty("player2")
    public Player getPlayer2() {
        return player2;
    }

    @JsonProperty("player2")
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    @JsonProperty("inningEvent")
    public String getInningEvent() {
        return inningEvent;
    }

    @JsonProperty("inningEvent")
    public void setInningEvent(String inningEvent) {
        this.inningEvent = inningEvent;
    }

    @JsonProperty("inningEvent2")
    public String getInningEvent2() {
        return inningEvent2;
    }

    @JsonProperty("inningEvent2")
    public void setInningEvent2(String inningEvent2) {
        this.inningEvent2 = inningEvent2;
    }

    @JsonProperty("team1Abbr")
    public String getTeam1Abbr() {
        return team1Abbr;
    }

    @JsonProperty("team1Abbr")
    public void setTeam1Abbr(String team1Abbr) {
        this.team1Abbr = team1Abbr;
    }

    @JsonProperty("team2Abbr")
    public String getTeam2Abbr() {
        return team2Abbr;
    }

    @JsonProperty("team2Abbr")
    public void setTeam2Abbr(String team2Abbr) {
        this.team2Abbr = team2Abbr;
    }

    @JsonProperty("team1Score")
    public String getTeam1Score() {
        return (team1Score==null?"0":team1Score);
    }

    @JsonProperty("team1Score")
    public void setTeam1Score(String team1Score) {
        this.team1Score = team1Score;
    }

    @JsonProperty("team2Score")
    public String getTeam2Score() {
        return (team2Score==null?"0":team2Score);
    }

    @JsonProperty("team2Score")
    public void setTeam2Score(String team2Score) {
        this.team2Score = team2Score;
    }

    @JsonProperty("team1AbbrEvent2")
    public String getTeam1AbbrEvent2() {
        return team1AbbrEvent2;
    }

    @JsonProperty("team1AbbrEvent2")
    public void setTeam1AbbrEvent2(String team1AbbrEvent2) {
        this.team1AbbrEvent2 = team1AbbrEvent2;
    }

    @JsonProperty("team2AbbrEvent2")
    public String getTeam2AbbrEvent2() {
        return team2AbbrEvent2;
    }

    @JsonProperty("team2AbbrEvent2")
    public void setTeam2AbbrEvent2(String team2AbbrEvent2) {
        this.team2AbbrEvent2 = team2AbbrEvent2;
    }

    @JsonProperty("team1ScoreEvent2")
    public String getTeam1ScoreEvent2() {
        return (team1ScoreEvent2==null?"0":team1ScoreEvent2);
    }

    @JsonProperty("team1ScoreEvent2")
    public void setTeam1ScoreEvent2(String team1ScoreEvent2) {
        this.team1ScoreEvent2 = team1ScoreEvent2;
    }

    @JsonProperty("team2ScoreEvent2")
    public String getTeam2ScoreEvent2() {
        return (team2ScoreEvent2==null?"0":team2ScoreEvent2);
    }

    @JsonProperty("team2ScoreEvent2")
    public void setTeam2ScoreEvent2(String team2ScoreEvent2) {
        this.team2ScoreEvent2 = team2ScoreEvent2;
    }

    @JsonProperty("startTimeEvent1")
    public long getStartTimeEvent1() {
        return startTimeEvent1;
    }

    @JsonProperty("startTimeEvent1")
    public void setStartTimeEvent1(long startTimeEvent1) {
        this.startTimeEvent1 = startTimeEvent1;
    }

    @JsonProperty("startTimeEvent2")
    public long getStartTimeEvent2() {
        return startTimeEvent2;
    }

    @JsonProperty("startTimeEvent2")
    public void setStartTimeEvent2(long startTimeEvent2) {
        this.startTimeEvent2 = startTimeEvent2;
    }

    @JsonProperty("team1locationType")
    public String getTeam1locationType() {
        return team1locationType;
    }

    @JsonProperty("team1locationType")
    public void setTeam1locationType(String team1locationType) {
        this.team1locationType = team1locationType;
    }

    @JsonProperty("team2locationType")
    public String getTeam2locationType() {
        return team2locationType;
    }

    @JsonProperty("team2locationType")
    public void setTeam2locationType(String team2locationType) {
        this.team2locationType = team2locationType;
    }

    @JsonProperty("team1locationTypePlayer2")
    public String getTeam1locationTypePlayer2() {
        return team1locationTypePlayer2;
    }

    @JsonProperty("team1locationTypePlayer2")
    public void setTeam1locationTypePlayer2(String team1locationTypePlayer2) {
        this.team1locationTypePlayer2 = team1locationTypePlayer2;
    }

    @JsonProperty("team2locationTypePlayer2")
    public String getTeam2locationTypePlayer2() {
        return team2locationTypePlayer2;
    }

    @JsonProperty("team2locationTypePlayer2")
    public void setTeam2locationTypePlayer2(String team2locationTypePlayer2) {
        this.team2locationTypePlayer2 = team2locationTypePlayer2;
    }

    public String getProps() {
        String props = "";
        for (int i = 0; i < propParameters.size(); i++) {
            if (i == propParameters.size() - 1) {
                props = props + propParameters.get(i).getAbbreviation();
            } else {
                props = props + propParameters.get(i).getAbbreviation() + " + ";
            }
        }
        return props;
    }

    public String getPropsPlayer1() {
        String props = "";
        for (int i = 0; i < propParameters.size(); i++) {
            if (propParameters.get(i).getBelongsToPlayer1()) {
                props = props + propParameters.get(i).getAbbreviation() + " + ";
            }
        }
        if (!props.isEmpty()) {
            props = props.substring(0, props.length() - 2);
        }
        return props;
    }

    public String getPropsPlayer2() {
        String props = "";
        for (int i = 0; i < propParameters.size(); i++) {
            if (!propParameters.get(i).getBelongsToPlayer1()) {

                props = props + propParameters.get(i).getAbbreviation() + " + ";
            }
        }
        if (!props.isEmpty()) {
            props = props.substring(0, props.length() - 2);
        }
        return props;
    }

    public String getPlayer1RealName() {


        return getPlayer1Name().replace(" ", " \n");
    }

}

