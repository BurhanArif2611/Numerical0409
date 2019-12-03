package com.pixelmarketo.nrh.models.Vender_history_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Example implements Serializable {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
