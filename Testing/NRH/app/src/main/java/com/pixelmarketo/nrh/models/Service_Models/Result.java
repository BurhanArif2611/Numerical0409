package com.pixelmarketo.nrh.models.Service_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    @SerializedName("subservice")
    @Expose
    private List<Subservice> subservice = null;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("service_image")
    @Expose
    private String serviceImage;
    @SerializedName("banners_image")
    @Expose
    private String banners_image;

    public String getBanners_image() {
        return banners_image;
    }

    public void setBanners_image(String banners_image) {
        this.banners_image = banners_image;
    }

    public List<Subservice> getSubservice() {
        return subservice;
    }

    public void setSubservice(List<Subservice> subservice) {
        this.subservice = subservice;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

}
