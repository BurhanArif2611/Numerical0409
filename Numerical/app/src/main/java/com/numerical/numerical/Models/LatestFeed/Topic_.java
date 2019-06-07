package com.numerical.numerical.Models.LatestFeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Topic_  implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("topic")
    @Expose
    private String topic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
