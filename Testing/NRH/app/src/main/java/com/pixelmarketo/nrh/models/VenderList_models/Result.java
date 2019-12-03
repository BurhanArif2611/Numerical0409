package com.pixelmarketo.nrh.models.VenderList_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("services")
    @Expose
    private String services;
    @SerializedName("bid_amount")
    @Expose
    private String bidAmount;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("accept_reject_bid")
    @Expose
    private String acceptRejectBid;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("city")
    @Expose
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAcceptRejectBid() {
        return acceptRejectBid;
    }

    public void setAcceptRejectBid(String acceptRejectBid) {
        this.acceptRejectBid = acceptRejectBid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
