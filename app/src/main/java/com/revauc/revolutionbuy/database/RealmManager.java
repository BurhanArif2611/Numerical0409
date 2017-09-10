package com.revauc.revolutionbuy.database;

import android.content.Context;

import com.revauc.revolutionbuy.util.LogUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmManager {
    static Realm realm;

    static RealmConfiguration realmConfiguration;
    public static int openCounter = 0;
    public static int closeCounter = 0;

    public static void initializeRealmConfig(Context appContext) {
        if (realmConfiguration == null) {
            RealmConfiguration realmConfiguration = new RealmConfiguration
                    .Builder()
                    .schemaVersion(0)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            setRealmConfiguration(realmConfiguration);

        }
    }

    public static void setRealmConfiguration(RealmConfiguration realmConfiguration) {
        RealmManager.realmConfiguration = realmConfiguration;
        Realm.setDefaultConfiguration(realmConfiguration);
    }


    public static Realm getRealm() {
        if (realm == null || realm.isClosed()) {
            realm = Realm.getDefaultInstance();
            openCounter++;
            LogUtils.LOGD("RealmManager", "while opening-->open counter-->" + openCounter + " close counter--->" + closeCounter);
        }

        return realm;
    }


    public static void releaseRealm() {
        Realm.compactRealm(realmConfiguration);
        closeRealmInstance(realm);
        LogUtils.LOGD("RealmManager", " ending releasing--> open counter-->" + openCounter + " close counter--->" + closeCounter);
        openCounter = 0;
        closeCounter = 0;

    }

    public static Realm getRealmNewInstance() {
        openCounter++;
        LogUtils.LOGD("RealmManager", "while opening for thread-->open counter-->" + openCounter + " close counter--->" + closeCounter);
        return Realm.getDefaultInstance();
    }


    public static void closeRealmInstance(Realm realm) {
        if (realm != null && !realm.isClosed()) {
            realm.close();
            realm = null;
            closeCounter++;
            LogUtils.LOGD("RealmManager", "while closing-->open counter-->" + openCounter + " close counter--->" + closeCounter);
        }
    }

    public static void deleteRealm() {
        try {
            if (realm != null && !realm.isClosed()) {
                closeRealmInstance(realm);
            }
            if (realmConfiguration != null)
                Realm.deleteRealm(realmConfiguration);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
