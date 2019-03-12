package com.revauc.revolutionbuy.network.request.auth;



public class UnlockPhoneRequest {

    private int buyerProductId;
    private int sellerId;
    private String transactionId;
    private String base64Receipt;

    public UnlockPhoneRequest(int buyerProductId, int sellerId, String transactionId, String base64Receipt) {
        this.buyerProductId = buyerProductId;
        this.sellerId = sellerId;
        this.transactionId = transactionId;
        this.base64Receipt = base64Receipt;
    }

    public int getBuyerProductId() {
        return buyerProductId;
    }

    public void setBuyerProductId(int buyerProductId) {
        this.buyerProductId = buyerProductId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBase64Receipt() {
        return base64Receipt;
    }

    public void setBase64Receipt(String base64Receipt) {
        this.base64Receipt = base64Receipt;
    }
}
