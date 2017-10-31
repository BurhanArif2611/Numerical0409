package com.revauc.revolutionbuy.network.request.auth;


public class BuyerPaymentRequest {


    private String buyerStripeToken;
    private int amount;
    private int destinationAmount;
    private int sellerProductId;
    private int sellerId;
    private int buyerProductId;

    public BuyerPaymentRequest(String buyerStripeToken, int amount, int destinationAmount, int sellerProductId, int sellerId, int buyerProductId) {
        this.buyerStripeToken = buyerStripeToken;
        this.amount = amount;
        this.destinationAmount = destinationAmount;
        this.sellerProductId = sellerProductId;
        this.sellerId = sellerId;
        this.buyerProductId = buyerProductId;
    }

    public String getBuyerStripeToken() {
        return buyerStripeToken;
    }

    public void setBuyerStripeToken(String buyerStripeToken) {
        this.buyerStripeToken = buyerStripeToken;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDestinationAmount() {
        return destinationAmount;
    }

    public void setDestinationAmount(int destinationAmount) {
        this.destinationAmount = destinationAmount;
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

    public int getBuyerProductId() {
        return buyerProductId;
    }

    public void setBuyerProductId(int buyerProductId) {
        this.buyerProductId = buyerProductId;
    }
}
