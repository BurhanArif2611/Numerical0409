package com.revauc.revolutionbuy.analytics;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.revauc.revolutionbuy.network.response.UserDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;



public class AnalyticsManager {

    public static void trackMixpanelEvent(MixpanelAPI mixpanelAPI, String eventName) {
        mixpanelAPI.track(eventName);
    }

    public static void setDistictUser(MixpanelAPI mixpanelAPI, UserDto user) {
        if (user != null) {
            mixpanelAPI.identify("" + user.getId());
        } else {
            mixpanelAPI.clearSuperProperties();
            mixpanelAPI.reset();
        }
    }

    public static void setDistictUserSuperProperties(MixpanelAPI mixpanelAPI, String type, String action, UserDto personModel) {
        if (personModel == null) {
            return;
        }

        //Add person model to Mixpanel
        setPersonOnMixPanel(mixpanelAPI, personModel, action);

        //Add user properties
        setPersonUserPropertiesOnMixPanel(mixpanelAPI, type, personModel, action);

        //Add super properties
        setSuperPropertiesOnMixPanel(mixpanelAPI, type, personModel, action);
    }

    public static void setPersonOnMixPanel(MixpanelAPI mixpanelAPI, UserDto personModel, String action) {
        if (personModel != null) {
            mixpanelAPI.alias(personModel.getId() + "", mixpanelAPI.getDistinctId());
            mixpanelAPI.identify(mixpanelAPI.getDistinctId());
            mixpanelAPI.getPeople().set("created", new Date());
        }
    }

    public static void setPersonUserPropertiesOnMixPanel(MixpanelAPI mixpanelAPI, String type, UserDto personModel, String action) {
        if (personModel != null) {
            JSONObject props = new JSONObject();
            try {
                props.put("UserId", personModel.getId() + "");
                props.put("UserType", type);
                props.put("Action", action);
                props.put("Age", personModel.getAge() + "");
                props.put("selectedCity", personModel.getCity().getName());
                props.put("selectedCountry", personModel.getCity().getState().getCountry().getName());
                props.put("selectedState", personModel.getCity().getState().getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mixpanelAPI.getPeople().set(props);
        }
    }

    public static void setSuperPropertiesOnMixPanel(MixpanelAPI mixpanelAPI, String type, UserDto personModel, String action) {
        if (personModel == null) {
            return;
        }

        JSONObject props = new JSONObject();
        try {
            props.put("UserId", personModel.getId() + "");
            props.put("UserType", type);
            props.put("Action", action);
            props.put("Phone", personModel.getMobile());
            props.put("Email", personModel.getEmail());
            props.put("Name", personModel.getName());
            props.put("Age", personModel.getAge() + "");
            props.put("selectedCity", personModel.getCity().getName());
            props.put("selectedCountry", personModel.getCity().getState().getCountry().getName());
            props.put("selectedState", personModel.getCity().getState().getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mixpanelAPI.registerSuperProperties(props);

    }
}