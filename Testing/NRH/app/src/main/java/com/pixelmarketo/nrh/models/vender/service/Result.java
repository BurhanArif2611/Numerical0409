package com.pixelmarketo.nrh.models.vender.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pixelmarketo.nrh.models.SelectImage;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("bid")
    @Expose
    private String bid;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_date")
    @Expose
    private String eventDate;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("no_of_days")
    @Expose
    private String noOfDays;
    @SerializedName("no_horse")
    @Expose
    private String no_horse;
    @SerializedName("no_of_watercan")
    @Expose
    private String noOfWatercan;
    @SerializedName("from_place")
    @Expose
    private String fromPlace;
    @SerializedName("to_place")
    @Expose
    private String toPlace;
    @SerializedName("pick_up_time")
    @Expose
    private String pickUpTime;
    @SerializedName("no_of_baggi_horse")
    @Expose
    private String noOfBaggiHorse;
    @SerializedName("singer_dancer")
    @Expose
    private String singerDancer;
    @SerializedName("cantens")
    @Expose
    private String cantens;
    @SerializedName("no_of_dhol")
    @Expose
    private String noOfDhol;
    @SerializedName("no_of_member")
    @Expose
    private String noOfMember;
    @SerializedName("no_of_item")
    @Expose
    private String noOfItem;
    @SerializedName("vaters")
    @Expose
    private String vaters;
    @SerializedName("decoretion_type")
    @Expose
    private String decoretionType;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("tehsil")
    @Expose
    private String tehsil;
    @SerializedName("vehical_type")
    @Expose
    private String vehical_type;
    @SerializedName("drone_crean")
    @Expose
    private String drone_crean;
    @SerializedName("pre_vid_shutting")
    @Expose
    private String pre_vid_shutting;
    @SerializedName("led_wall")
    @Expose
    private String led_wall;
    @SerializedName("vid_cd_hours")
    @Expose
    private String vid_cd_hours;

    public String getNo_horse() {
        return no_horse;
    }

    public void setNo_horse(String no_horse) {
        this.no_horse = no_horse;
    }

    public String getDrone_crean() {
        return drone_crean;
    }

    public void setDrone_crean(String drone_crean) {
        this.drone_crean = drone_crean;
    }

    public String getPre_vid_shutting() {
        return pre_vid_shutting;
    }

    public void setPre_vid_shutting(String pre_vid_shutting) {
        this.pre_vid_shutting = pre_vid_shutting;
    }

    public String getLed_wall() {
        return led_wall;
    }

    public void setLed_wall(String led_wall) {
        this.led_wall = led_wall;
    }

    public String getVid_cd_hours() {
        return vid_cd_hours;
    }

    public void setVid_cd_hours(String vid_cd_hours) {
        this.vid_cd_hours = vid_cd_hours;
    }

    @SerializedName("image")
    @Expose
    private List<Image> image = null;

    public String getVehical_type() {
        return vehical_type;
    }

    public void setVehical_type(String vehical_type) {
        this.vehical_type = vehical_type;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getNoOfWatercan() {
        return noOfWatercan;
    }

    public void setNoOfWatercan(String noOfWatercan) {
        this.noOfWatercan = noOfWatercan;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getNoOfBaggiHorse() {
        return noOfBaggiHorse;
    }

    public void setNoOfBaggiHorse(String noOfBaggiHorse) {
        this.noOfBaggiHorse = noOfBaggiHorse;
    }

    public String getSingerDancer() {
        return singerDancer;
    }

    public void setSingerDancer(String singerDancer) {
        this.singerDancer = singerDancer;
    }

    public String getCantens() {
        return cantens;
    }

    public void setCantens(String cantens) {
        this.cantens = cantens;
    }

    public String getNoOfDhol() {
        return noOfDhol;
    }

    public void setNoOfDhol(String noOfDhol) {
        this.noOfDhol = noOfDhol;
    }

    public String getNoOfMember() {
        return noOfMember;
    }

    public void setNoOfMember(String noOfMember) {
        this.noOfMember = noOfMember;
    }

    public String getNoOfItem() {
        return noOfItem;
    }

    public void setNoOfItem(String noOfItem) {
        this.noOfItem = noOfItem;
    }

    public String getVaters() {
        return vaters;
    }

    public void setVaters(String vaters) {
        this.vaters = vaters;
    }

    public String getDecoretionType() {
        return decoretionType;
    }

    public void setDecoretionType(String decoretionType) {
        this.decoretionType = decoretionType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }
}
