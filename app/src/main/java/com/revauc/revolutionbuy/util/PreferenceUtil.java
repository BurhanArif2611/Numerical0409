package com.revauc.revolutionbuy.util;

import android.location.Location;

import com.revauc.revolutionbuy.network.response.auth.UserDto;
import com.orhanobut.hawk.Hawk;

/**
 * Created by gautambisht on 11/11/16.
 */

public final class PreferenceUtil {

    public static final String KEY_APP_STATE = "APP_STATE";
    public static final String KEY_FB_TOKEN = "FB_TOKEN";
    public static final String KEY_IS_FB_LOGIN = "FB_LOGIN";
    public static final String KEY_LOGGED_IN = "LOGGED_IN";
    public static final String KEY_SHOW_ENTER_APP = "SHOW_ENTER_APP";
    private static final String KEY_USER_LOCATION = "USER_LOCATION";
    private static final String KEY_USER_DATA = "USER_DATA";
    private static final String KEY_SORT_LIVE = "LIVE_SORT_SELECTED";
    private static final String KEY_SORT_HISTORY = "HISTORY_SORT_SELECTED";
    private static final String KEY_PREFERENCE_LIVE_FEATURED = "IS_LIVE_FEATURED";
    private static final String KEY_PREFERENCE_HISTORY_FEATURED = "IS_HISTORY_FEATURED";
    private static final String KEY_FCM = "FCM_TOKEN_KEY";
    private static final String KEY_INVITE_TO_JOIN = "INVITE_TO_JOIN";
    private static String KEY_AUTH_TOKEN = "AUTH_TOKEN";

    private PreferenceUtil() {

    }

    public static void saveAppState(Object state) {
        Hawk.put(KEY_APP_STATE, state);
    }

    public static Object getAppState() {
        return Hawk.get(KEY_APP_STATE);
    }

    public static String getFacebookToken() {
        return Hawk.get(KEY_FB_TOKEN);
    }

    public static void setFacebookToken(String value) {
        Hawk.put(KEY_FB_TOKEN, value);
    }

    public static boolean isLoggedIn() {
        return Hawk.get(KEY_LOGGED_IN, false);
    }

    public static void setLoggedIn(boolean value) {
        Hawk.put(KEY_LOGGED_IN, value);
    }

    public static boolean shouldShowEnterApp() {
        return Hawk.get(KEY_SHOW_ENTER_APP, false);
    }

    public static void setShowEnterApp(boolean value) {
        Hawk.put(KEY_SHOW_ENTER_APP, value);
    }

    //USER LOCATION
    public static void setCurrentLocation(Location currentLocation) {
        Hawk.put(KEY_USER_LOCATION, currentLocation);
    }

    public static Location getCurrentLocation() {
        return Hawk.get(KEY_USER_LOCATION, null);
    }

    public static void reset() {
        Hawk.deleteAll();
        setShowEnterApp(true);
    }

    public static void setUserProfile(UserDto userData) {
        Hawk.put(KEY_USER_DATA, userData);
    }

    public static UserDto getUserProfile() {
        return Hawk.get(KEY_USER_DATA);
    }

    public static void setAuthToken(String accessToken) {
        Hawk.put(KEY_AUTH_TOKEN, accessToken);
    }

    public static String getAuthToken() {
        return Hawk.get(KEY_AUTH_TOKEN);
    }


    public static void setLiveSort(int sort) {
        Hawk.put(KEY_SORT_LIVE, sort);
    }

    public static int getSortLive() {
        return Hawk.get(KEY_SORT_LIVE, 0);
    }

    public static void setHistorySort(int sort) {
        Hawk.put(KEY_SORT_HISTORY, sort);
    }

    public static int getSortHistory() {
        return Hawk.get(KEY_SORT_HISTORY, 0);
    }


    public static void setFCMToken(String fcmToken) {
        Hawk.put(KEY_FCM, fcmToken);
    }

    public static String getFCMToken() {
        return Hawk.get(KEY_FCM);
    }

    public static void setLiveFeaturedSelection(boolean fcmToken) {
        Hawk.put(KEY_PREFERENCE_LIVE_FEATURED, fcmToken);
    }

    public static boolean isLiveFeatured() {
        return Hawk.get(KEY_PREFERENCE_LIVE_FEATURED, false);
    }

    public static void setHistoryFeaturedSelection(boolean fcmToken) {
        Hawk.put(KEY_PREFERENCE_HISTORY_FEATURED, fcmToken);
    }

    public static boolean isHistoryFeatured() {
        return Hawk.get(KEY_PREFERENCE_HISTORY_FEATURED, false);
    }

    public static void setFbLogin(boolean isFbLogin) {
        Hawk.put(KEY_IS_FB_LOGIN, isFbLogin);
    }

    public static boolean isFbLogin() {
        return Hawk.get(KEY_IS_FB_LOGIN, false);
    }

    public static void setInviteToJoin(int inviteToJoin) {
        Hawk.put(KEY_INVITE_TO_JOIN, inviteToJoin);
    }

    public static int getInviteToJoin() {
        return Hawk.get(KEY_INVITE_TO_JOIN, 0);
    }
}
