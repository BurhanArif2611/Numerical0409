package com.pixelmarketo.nrh.models.Service_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Subservice implements Serializable {
    @SerializedName("sub_service_name")
    @Expose
    private String subServiceName;
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
    }

}
