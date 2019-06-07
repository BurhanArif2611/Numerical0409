package com.numerical.numerical.Models.LatestFeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Example implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("topic")
    @Expose
    private Topic topic;
    @SerializedName("assetPath")
    @Expose
    private String assetPath;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("isCollection")
    @Expose
    private Boolean isCollection;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("mode")
    @Expose
    private Integer mode;
    @SerializedName("relatedContentURL")
    @Expose
    private String relatedContentURL;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("numerons")
    @Expose
    private List<Numeron> numerons = null;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("lastModified")
    @Expose
    private String lastModified;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("actions")
    @Expose
    private List<Action> actions = null;
    @SerializedName("likedAnon")
    @Expose
    private List<LikedAnon> likedAnon = null;
    @SerializedName("likedUsers")
    @Expose
    private List<Object> likedUsers = null;
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("type")
    @Expose
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public void setAssetPath(String assetPath) {
        this.assetPath = assetPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Boolean isCollection) {
        this.isCollection = isCollection;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getRelatedContentURL() {
        return relatedContentURL;
    }

    public void setRelatedContentURL(String relatedContentURL) {
        this.relatedContentURL = relatedContentURL;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<Numeron> getNumerons() {
        return numerons;
    }

    public void setNumerons(List<Numeron> numerons) {
        this.numerons = numerons;
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

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<LikedAnon> getLikedAnon() {
        return likedAnon;
    }

    public void setLikedAnon(List<LikedAnon> likedAnon) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
