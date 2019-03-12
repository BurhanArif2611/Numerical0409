

package com.revauc.revolutionbuy.network.response;


public class InAppPurchasedModel {

    private String orderId;
    private String packageName;
    private String productId;
    private String purchaseTime;
    private int purchaseState;
    private String purchaseToken;

    public String getOrderId() {
        return orderId;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getProductId() {
        return productId;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public int getPurchaseState() {
        return purchaseState;
    }

    public String getPurchaseToken() {
        return purchaseToken;
    }
}
