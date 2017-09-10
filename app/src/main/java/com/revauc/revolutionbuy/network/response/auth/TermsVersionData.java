package com.revauc.revolutionbuy.network.response.auth;


import com.revauc.revolutionbuy.network.BaseResponse;

/**
 *
 */
public class TermsVersionData extends BaseResponse {

    private String currentUserVersion;
    private String currentTermsConditionVersion;
    private boolean versionMisMatch;

    public String getCurrentUserVersion() {
        return currentUserVersion;
    }

    public String getCurrentTermsConditionVersion() {
        return currentTermsConditionVersion;
    }

    public boolean isVersionMisMatch() {
        return versionMisMatch;
    }
}