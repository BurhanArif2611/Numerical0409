package com.revauc.revolutionbuy.network.request.auth;


public class BuyerCompleteTransactionRequest {

    private String transactionDescription;
    private int buyerProductId;
    private int sellerProductId;
    private int sellerId;
    private int buyerId;
    private String title;

    public BuyerCompleteTransactionRequest(String transactionDescription, int buyerProductId, int sellerProductId, int sellerId) {
        this.transactionDescription = transactionDescription;
        this.buyerProductId = buyerProductId;
        this.sellerProductId = sellerProductId;
        this.sellerId = sellerId;
    }

    public BuyerCompleteTransactionRequest(String transactionDescription,String title, int buyerProductId, int sellerProductId, int buyerId) {
        this.transactionDescription = transactionDescription;
        this.buyerProductId = buyerProductId;
        this.sellerProductId = sellerProductId;
        this.buyerId = buyerId;
        this.title = title;
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

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
