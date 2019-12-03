package com.pixelmarketo.nrh.models.Service_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Banner implements Serializable {

    @SerializedName("advertisement_image")
    @Expose
    private String advertisement_image;

    public String getAdvertisement_image() {
        return advertisement_image;
    }

    public void setAdvertisement_image(String advertisement_image) {
        this.advertisement_image = advertisement_image;
    }
}
