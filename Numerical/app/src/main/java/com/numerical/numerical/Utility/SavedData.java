package com.numerical.numerical.Utility;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.numerical.numerical.MyApplication;

public class SavedData {

    private static final String PASSWORD = "passsword";
    private static final String TOKAN = "tokan";
    private static final String Check = "check";
    private static final String ACCOUNT = "account";



    static SharedPreferences prefs;

    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        }
        return prefs;
    }

    public static String getPassword() {
        return getInstance().getString(PASSWORD, "");
    }

    public static void savePassword(String passsword) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(PASSWORD, passsword);
        editor.apply();
    }

    public static String getTokan() {
        return getInstance().getString(TOKAN, "");
    }

    public static void saveTokan(String tokan) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(TOKAN, tokan);
        editor.apply();
    }


}