package com.revauc.revolutionbuy.network.request.auth;



public class SellerReportItemRequest {

    private String description;
    private int sellerId;

    public SellerReportItemRequest(String description, int sellerId) {
        this.description = description;
        this.sellerId = sellerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
