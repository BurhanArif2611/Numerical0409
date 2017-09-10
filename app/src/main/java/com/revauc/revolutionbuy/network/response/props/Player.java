
package com.revauc.revolutionbuy.network.response.props;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player implements Parcelable {


//    "id":13,
//            "playerId":100,
//            "leagueType":"MLB",
//            "firstName":"fname",
//            "lastName":"lname",
//            "weight":40,
//            "height":6,
//            "uniform":"test",
//            "positionAbbreviation":"test",
//            "image":null,

    //            "teamAbbr":"NYY",
//            "locationType":"Away",
//            "propParameters":[
//            "Hits",
//            "Homeruns",
//            "Strikeouts"
//            ]


//    "id":13,
////                "playerId":100,
////                "leagueType":"MLB",
////                "firstName":"fname",
////                "lastName":"lname",
////                "weight":40,
////                "height":6,
////                "uniform":"test",
////                "positionAbbreviation":"test",
////                "image":null,
////                "teamAbbr":"NYY",
////                "propParameters":[
////        "HITS",
////                "HR",
////                "ST"
////]
////


    private List<String> propParameters = null;
    private String playerId;
    private int id;
    private String leagueType;
    private String firstName;
    private String lastName;
    private String imageOrg;
    private Integer weight;
    private Float height;
    private String uniform;
    private String positionAbbreviation;
    private String image;
    private String teamAbbr;


    public List<String> getPropParameters() {
        return propParameters;
    }

    public void setPropParameters(List<String> propParameters) {
        this.propParameters = propParameters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTeamAbbr() {
        return teamAbbr;
    }

    public void setTeamAbbr(String teamAbbr) {
        this.teamAbbr = teamAbbr;
    }


    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }


    public String getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(String leagueType) {
        this.leagueType = leagueType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageOrg() {
        return imageOrg;
    }

    public void setImageOrg(String imageOrg) {
        this.imageOrg = imageOrg;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getUniform() {
        return uniform;
    }

    public void setUniform(String uniform) {
        this.uniform = uniform;
    }

    public String getPositionAbbreviation() {
        return positionAbbreviation;
    }

    public void setPositionAbbreviation(String positionAbbreviation) {
        this.positionAbbreviation = positionAbbreviation;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }


    public Player() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.propParameters);
        dest.writeString(this.playerId);
        dest.writeInt(this.id);
        dest.writeString(this.leagueType);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.imageOrg);
        dest.writeValue(this.weight);
        dest.writeValue(this.height);
        dest.writeString(this.uniform);
        dest.writeString(this.positionAbbreviation);
        dest.writeString(this.image);
        dest.writeString(this.teamAbbr);
    }

    protected Player(Parcel in) {
        this.propParameters = in.createStringArrayList();
        this.playerId = in.readString();
        this.id = in.readInt();
        this.leagueType = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.imageOrg = in.readString();
        this.weight = (Integer) in.readValue(Integer.class.getClassLoader());
        this.height = (Float) in.readValue(Float.class.getClassLoader());
        this.uniform = in.readString();
        this.positionAbbreviation = in.readString();
        this.image = in.readString();
        this.teamAbbr = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getProps() {
        String props = "";
        for (int i = 0; i < propParameters.size(); i++) {
            if (i == propParameters.size()-1) {
                props = props + propParameters.get(i);
            } else {
                props =props+ propParameters.get(i) + " + ";
            }

        }
        return props;
    }
}
