package com.numerical.numerical.Models.Topics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Example implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("assetPath")
    @Expose
    private String assetPath;
    @SerializedName("numeronCount")
    @Expose
    private Integer numeronCount;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("topic")
    @Expose
    private String topic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public void setAssetPath(String assetPath) {
        this.assetPath = assetPath;
    }

    public Integer getNumeronCount() {
        return numeronCount;
    }

    public void setNumeronCount(Integer numeronCount) {
        this.numeronCount = numeronCount;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
