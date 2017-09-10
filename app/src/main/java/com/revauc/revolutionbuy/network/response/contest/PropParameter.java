package com.revauc.revolutionbuy.network.response.contest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hemant on 22/06/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropParameter {
    @JsonProperty("propParamId")
    private Integer propParamId;
    @JsonProperty("leagueType")
    private String leagueType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("abbreviation")
    private String abbreviation;
    @JsonProperty("liveStatsPropValue")
    private Integer liveStatsPropValue;
    @JsonProperty("belongsToPlayer1")
    private Boolean belongsToPlayer1;

    @JsonProperty("propParamId")
    public Integer getPropParamId() {
        return propParamId;
    }

    @JsonProperty("propParamId")
    public void setPropParamId(Integer propParamId) {
        this.propParamId = propParamId;
    }

    @JsonProperty("leagueType")
    public String getLeagueType() {
        return leagueType;
    }

    @JsonProperty("leagueType")
    public void setLeagueType(String leagueType) {
        this.leagueType = leagueType;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("abbreviation")
    public String getAbbreviation() {
        return abbreviation;
    }

    @JsonProperty("abbreviation")
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @JsonProperty("liveStatsPropValue")
    public Integer getLiveStatsPropValue() {
        return liveStatsPropValue;
    }

    @JsonProperty("liveStatsPropValue")
    public void setLiveStatsPropValue(Integer liveStatsPropValue) {
        this.liveStatsPropValue = liveStatsPropValue;
    }

    @JsonProperty("belongsToPlayer1")
    public Boolean getBelongsToPlayer1() {
        return belongsToPlayer1;
    }

    @JsonProperty("belongsToPlayer1")
    public void setBelongsToPlayer1(Boolean belongsToPlayer1) {
        this.belongsToPlayer1 = belongsToPlayer1;
    }

}
