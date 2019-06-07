package com.numerical.numerical.Models.LatestFeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Numeron implements Serializable {
    @SerializedName("numeral")
    @Expose
    private String numeral;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("topic")
    @Expose
    private Topic topic;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("assetPath")
    @Expose
    private String assetPath;
    @SerializedName("assetInternal")
    @Expose
    private Boolean assetInternal;
    @SerializedName("assetType")
    @Expose
    private String assetType;
    @SerializedName("subType")
    @Expose
    private String subType;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("lastModified")
    @Expose
    private String lastModified;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("actions")
    @Expose
    private List<Object> actions = null;
    @SerializedName("likedAnon")
    @Expose
    private List<Object> likedAnon = null;
    @SerializedName("likedUsers")
    @Expose
    private List<Object> likedUsers = null;
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("type")
    @Expose
    private Integer type;

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getNumeral() {
        return numeral;
    }

    public void setNumeral(String numeral) {
        this.numeral = numeral;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<Object> getActions() {
        return actions;
    }

    public void setActions(List<Object> actions) {
        this.actions = actions;
    }

    public List<Object> getLikedAnon() {
        return likedAnon;
    }

    public void setLikedAnon(List<Object> likedAnon) {
        this.likedAnon = likedAnon;
    }

    public List<Object> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<Object> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public void setAssetPath(String assetPath) {
        this.assetPath = assetPath;
    }

    public Boolean getAssetInternal() {
        return assetInternal;
    }

    public void setAssetInternal(Boolean assetInternal) {
        this.assetInternal = assetInternal;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }
}
