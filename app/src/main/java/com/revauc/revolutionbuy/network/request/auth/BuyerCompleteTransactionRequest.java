package com.revauc.revolutionbuy.network.request.auth;

/*
Copyright © 2017 Block Partee. All rights reserved.
Developed by Appster.
*/

public class BuyerCompleteTransactionRequest {

    private String transactionDescription;
    private int buyerProductId;
    private int sellerProductId;
    private int sellerId;

    public BuyerCompleteTransactionRequest(String transactionDescription, int buyerProductId, int sellerProductId, int sellerId) {
        this.transactionDescription = transactionDescription;
        this.buyerProductId = buyerProductId;
        this.sellerProductId = sellerProductId;
        this.sellerId = sellerId;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public int getBuyerProductId() {
        return buyerProductId;
    }

    public void setBuyerProductId(int buyerProductId) {
        this.buyerProductId = buyerProductId;
    }

    public int getSellerProductId() {
        return sellerProductId;
    }

    public void setSellerProductId(int sellerProductId) {
        this.sellerProductId = sellerProductId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
