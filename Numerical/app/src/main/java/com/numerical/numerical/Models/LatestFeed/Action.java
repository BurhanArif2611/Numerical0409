package com.numerical.numerical.Models.LatestFeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Action implements Serializable {
    @SerializedName("actionInternal")
    @Expose
    private Boolean actionInternal;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("_id")
    @Expose
    private String id;

    public Boolean getActionInternal() {
        return actionInternal;
    }

    public void setActionInternal(Boolean actionInternal) {
        this.actionInternal = actionInternal;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
