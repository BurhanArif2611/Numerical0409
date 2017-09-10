package com.revauc.revolutionbuy.network.response.contest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropsDelta {
    @JsonProperty("propId")
    private Integer propId;
    @JsonProperty("livePropPointsPlayer2")
    private Integer livePropPointsPlayer2;
    @JsonProperty("livePropPoints")
    private Integer livePropPoints;
    @JsonProperty("inningEvent")
    private String inningEvent;
    @JsonProperty("inningEvent2")
    private String inningEvent2;
    @JsonProperty("team1Score")
    private String team1Score;
    @JsonProperty("team2Score")
    private String team2Score;
    @JsonProperty("team1ScoreEvent2")
    private String team1ScoreEvent2;
    @JsonProperty("team2ScoreEvent2")
    private String team2ScoreEvent2;
    @JsonProperty("event1Complete")
    private boolean event1Complete;
    @JsonProperty("event2Complete")
    private boolean event2Complete;
    @JsonProperty("propDisabled")
    private boolean propDisabled;

    @JsonProperty("propId")
    public Integer getPropId() {
        return propId;
    }

    @JsonProperty("propId")
    public void setPropId(Integer propId) {
        this.propId = propId;
    }


    @JsonProperty("livePropPoints")
    public Integer getLivePropPoints() {
        return livePropPoints;
    }

    @JsonProperty("livePropPoints")
    public void setLivePropPoints(Integer livePropPoints) {
        this.livePropPoints = livePropPoints;
    }

    @JsonProperty("livePropPointsPlayer2")
    public Integer getLivePropPointsPlayer2() {
        return livePropPointsPlayer2;
    }

    @JsonProperty("livePropPointsPlayer2")
    public void setLivePropPointsPlayer2(Integer livePropPointsPlayer2) {
        this.livePropPointsPlayer2 = livePropPointsPlayer2;
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

    @JsonProperty("team1Score")
    public String getTeam1Score() {
        return team1Score;
    }

    @JsonProperty("team1Score")
    public void setTeam1Score(String team1Score) {
        this.team1Score = team1Score;
    }

    @JsonProperty("team2Score")
    public String getTeam2Score() {
        return team2Score;
    }

    @JsonProperty("team2Score")
    public void setTeam2Score(String team2Score) {
        this.team2Score = team2Score;
    }

    @JsonProperty("team1ScoreEvent2")
    public String getTeam1ScoreEvent2() {
        return team1ScoreEvent2;
    }

    @JsonProperty("team1ScoreEvent2")
    public void setTeam1ScoreEvent2(String team1ScoreEvent2) {
        this.team1ScoreEvent2 = team1ScoreEvent2;
    }

    @JsonProperty("team2ScoreEvent2")
    public String getTeam2ScoreEvent2() {
        return team2ScoreEvent2;
    }

    @JsonProperty("team2ScoreEvent2")
    public void setTeam2ScoreEvent2(String team2ScoreEvent2) {
        this.team2ScoreEvent2 = team2ScoreEvent2;
    }

    @JsonProperty("event1Complete")
    public boolean getEvent1Complete() {
        return event1Complete;
    }

    @JsonProperty("event1Complete")
    public void setEvent1Complete(boolean event1Complete) {
        this.event1Complete = event1Complete;
    }

    @JsonProperty("event2Complete")
    public boolean getEvent2Complete() {
        return event2Complete;
    }

    @JsonProperty("event2Complete")
    public void setEvent2Complete(boolean event2Complete) {
        this.event2Complete = event2Complete;
    }

    @JsonProperty("propDisabled")
    public boolean getPropDisabled() {
        return propDisabled;
    }

    @JsonProperty("propDisabled")
    public void setPropDisabled(boolean propDisabled) {
        this.propDisabled = propDisabled;
    }
}

