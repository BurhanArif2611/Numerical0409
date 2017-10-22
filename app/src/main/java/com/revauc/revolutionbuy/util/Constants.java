package com.revauc.revolutionbuy.util;

import android.content.Context;
import android.support.annotation.IntDef;

import com.revauc.revolutionbuy.R;

import java.lang.annotation.Retention;

import static com.revauc.revolutionbuy.util.Constants.EnterType.EDIT_PICKS;
import static com.revauc.revolutionbuy.util.Constants.EnterType.ENTER;
import static com.revauc.revolutionbuy.util.Constants.EnterType.RE_ENTER;
import static com.revauc.revolutionbuy.util.Constants.LeagueType.LEAGUE_ALL;
import static com.revauc.revolutionbuy.util.Constants.LeagueType.LEAGUE_MLB;
import static com.revauc.revolutionbuy.util.Constants.LeagueType.LEAGUE_NBA;
import static com.revauc.revolutionbuy.util.Constants.LeagueType.LEAGUE_NFL;
import static com.revauc.revolutionbuy.util.Constants.NavigationMode.NAVIGATION_FEED;
import static com.revauc.revolutionbuy.util.Constants.NavigationMode.NAVIGATION_HISTORY;
import static com.revauc.revolutionbuy.util.Constants.NavigationMode.NAVIGATION_LIVE;
import static com.revauc.revolutionbuy.util.Constants.NavigationMode.NAVIGATION_NOTIFICATION;
import static com.revauc.revolutionbuy.util.Constants.NavigationMode.NAVIGATION_UPCOMING;
import static com.revauc.revolutionbuy.util.Constants.PickViewMode.VIEW_TRADITIONAL_PICK;
import static com.revauc.revolutionbuy.util.Constants.PickViewMode.VIEW_TRAD_SELECTED_PICK;
import static com.revauc.revolutionbuy.util.Constants.PickViewMode.VIEW_VERSUS_PICK;
import static com.revauc.revolutionbuy.util.Constants.PickViewMode.VIEW_VERSUS_SELECTED_PICK;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 *
 */
public class Constants {

    public static final String ERROR_UNSUPPORTED_SIZE = "UNSUPPORTED MEDIA SIZE";
    public static final String ERROR_CANNOT_UPLOAD = "Image size cannot exceeds 10 MB";
    public static final String EXTRA_FACEBOOK_TOKEN = "FACEBOOK_TOKEN";
    public static final String EXTRA_LEAGUE_TYPE = "LEAGUE_TYPE";
    public static final int ITEM_TYPE_SEPARATOR = 2;
    public static final String EXTRA_POLLING_REQUIRED = "POLLING_REQUIRED";
    public static final String EXTRA_SHOULD_ACCEPT = "SHOULD_ACCEPT";
    public static final String INTENT_ACTION_PLAYER_DISABLED = "com.revauc.revolutionbuy.playerdisable";
    public static final String EXTRA_CITY_ID = "CITY_ID";
    public static final String EXTRA_AGE = "AGE";
    public static final String EXTRA_MOBILE = "MOBILE";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_PRODUCT_DETAIL = "EXTRA_PRODUCT_DETAIL";
    public static final String EXTRA_PROFILE_IMAGE = "EXTRA_PROFILE_IMAGE";
    public static final String EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME";


    public static String BROAD_PICKS_ENTER_COMPLETE = "BROAD_PICKS_ENTER_COMPLETE";
    public static String CONTEST_CANCELLED = "CANCELLED";


    private Constants() {
    }

    public static final int SELECTED_UNDER = 1;
    public static final int SELECTED_OVER = 2;
    public static final int SELECTED_NONE = 0;

    public static final String EXTRA_ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String EXTRA_FROM_SETTINGS = "FROM_SETTINGS";
    public static final String EXTRA_IS_USER_DELETED = "IS_USER_DELETED";
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    public static final String EXTRA_USER_RANK = "EXTRA_USER_RANK";
    public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";
    public static final String EXTRA_CONTEST_ID = "EXTRA_CONTEST_ID";
    public static final String EXTRA_USER_CONTEST_ID = "EXTRA_USER_CONTEST_ID";
    public static final String EXTRA_OPPONENT_USER_CONTEST_ID = "EXTRA_OPPONENT_USER_CONTEST_ID";
    public static final String EXTRA_IS_FROM_PROFILE = "EXTRA_IS_FROM_PROFILE";
    public static final String EXTRA_CONTEST_TYPE = "EXTRA_CONTEST_TYPE";
    public static final String EXTRA_CONTEST_NAME = "EXTRA_CONTEST_NAME";
    public static final String EXTRA_CONTEST = "EXTRA_CONTEST";
    public static final String EXTRA_FACEBOOK_EMAIL = "FACEBOOK_EMAIL";
    public static final String EXTRA_LATITUDE = "LATITUDE";
    public static final String EXTRA_LONGITUDE = "LONGITUDE";
    public static final String EXTRA_PRICE = "Price";
    public static final String EXTRA_SORT_SELECTED = "SORT_SELECTED";
    public static final String EXTRA_IS_FEATURED_SELECTED = "FEATURED_SELECTED";
    public static final String EXTRA_ENTERIES = "ENTERIES";
    public static final String EXTRA_PRICE_CONTEST = "ENTERIES_PRICE";
    public static final String EXTRA_PLAYER = "EXTRA_PLAYER";
    public static final String EXTRA_PLAYER_TEAM = "EXTRA_PLAYER_TEAM_NAME";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String EXTRA_NOTIFICATION = "extra_notification";

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_MESSSAGE = "EXTRA_MESSAGE";
    public static final String EXTRA_RESULT_NEEDED = "EXTRA_RESULT_FLAG";
    public static final String EXTRA_IS_MULTIPLE_ENTRIES_ALLOWED = "IS_MULTIPLE_ENTRIES_ALLOWED";

    public static final int ERRORCODE_EMAIL_REQUIRED = 1;
    public static final int ERRORCODE_USERNAME_REQUIRED = 2;
    public static final int ERRORCODE_USERNAME_EXISTS = 3;

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int USER_NAME_MIN_LENGTH = 6;
    public static final int FIRST_NAME_MIN_LENGTH = 2;

    public static final int DEVICE_TYPE_ANDROID = 1;


    public static final String HOME = "Home";
    public static final String AWAY = "AWAY";

    //EEE dd MMM yyyy HH:mm:ss z
    public static final String DATE_FORMAT_SERVER = "yyyy-MM-dd HH:mm";//2017-05-15 11:22 AM GMT
    public static final String DATE_FORMAT_SERVER_STATS = "yyyy-MM-dd'T'HH:mm:ss";//2017-05-15 11:22 AM GMT

    //to display Sun, 20 Feb, 2017 | 6:00pm - 7:00pm
    public static final String DATE_FORMAT_DAY = "EEEE, MMM d$$, yyyy";// Sun, 20 Feb, 2017
    public static final String DATE_FORMAT_STATS = "MMM d$$, yyyy";// Sun, 20 Feb, 2017
    public static final String DATE_FORMAT_PICKS = "MMM d$$, yyyy | hh:mma";// Sun, 20 Feb, 2017 | 6:00pm
    public static final String DATE_FORMAT_EVENT_STATUS = "hh:mma";// Sun, 20 Feb, 2017
    public static final String DATE_FORMAT_TIME = "hh:mma";// 6:00pm
    public static final String DATE_FORMAT_TIME_SPACE = "hh:mm a";// 6:00 pm

    public enum ACTIVITIES {
        SWITCH_ACTIVITY
    }

    public enum FRAGMENTS {
        FEED_FRAGMENTS
    }

    public interface BundleKey {
        String LAYOUT_ID = "layoutResId";
        String INDEX = "INDEX";
    }

    public interface REQUEST_CODE {
        int REQUEST_CODE_LOGIN = 100;
        int FILTER_REQUEST_CODE = 101;
        int REQUEST_GALLERY = 102;
        int REQUEST_CAMERA = 103;
        int REQUEST_CODE_VOICE_SEARCH = 104;
        int REQUEST_CODE_FIRST_LOGIN = 105;

        int REQUEST_CODE_WRITE_SD_CARD = 200;
        int REQUEST_CODE_LOCATION_ACCESS = 201;
        int REQUEST_CODE_READ_PHONE_STATE = 202;
        int REQUEST_CODE_JOYRIDE = 203;
    }


    //Int Defs

    @Retention(SOURCE)
    @IntDef({LEAGUE_NBA, LEAGUE_NFL, LEAGUE_MLB, LEAGUE_ALL})
    public @interface LeagueType {
        int LEAGUE_ALL = 0;
        int LEAGUE_NBA = 1;
        int LEAGUE_MLB = 2;
        int LEAGUE_NFL = 3;
    }

    @Retention(SOURCE)
    @IntDef({ENTER, RE_ENTER, EDIT_PICKS})
    public @interface EnterType {
        int ENTER = 0;
        int RE_ENTER = 1;
        int EDIT_PICKS = 2;
    }


    @Retention(SOURCE)
    @IntDef({NAVIGATION_FEED, NAVIGATION_UPCOMING, NAVIGATION_LIVE, NAVIGATION_HISTORY, NAVIGATION_NOTIFICATION})
    public @interface NavigationMode {
        int NAVIGATION_FEED = 1;
        int NAVIGATION_UPCOMING = 2;
        int NAVIGATION_LIVE = 3;
        int NAVIGATION_HISTORY = 4;
        int NAVIGATION_NOTIFICATION = 5;
    }


    @Retention(SOURCE)
    @IntDef({VIEW_TRADITIONAL_PICK, VIEW_VERSUS_PICK, VIEW_TRAD_SELECTED_PICK, VIEW_VERSUS_SELECTED_PICK})
    public @interface PickViewMode {
        int VIEW_TRADITIONAL_PICK = 0;
        int VIEW_VERSUS_PICK = 1;
        int VIEW_TRAD_SELECTED_PICK = 2;
        int VIEW_VERSUS_SELECTED_PICK = 3;
    }


    public static String getNavigationModeName(Context mContext, @Constants.NavigationMode int mode) {

        switch (mode) {
            case NAVIGATION_FEED:
                return mContext.getString(R.string.sports);
            case NAVIGATION_HISTORY:
                return mContext.getString(R.string.history);
            case NAVIGATION_LIVE:
                return mContext.getString(R.string.live);
            case NAVIGATION_UPCOMING:
                return mContext.getString(R.string.upcoming);
        }
        return "";
    }

    public static String getLeagueName(Context mContext, @Constants.LeagueType int league) {

        switch (league) {
            case LEAGUE_ALL:
                return mContext.getString(R.string.all);
            case LEAGUE_MLB:
                return mContext.getString(R.string.MLB);
            case LEAGUE_NBA:
                return mContext.getString(R.string.NBA);
            case LEAGUE_NFL:
                return mContext.getString(R.string.NFL);
        }
        return "";
    }


    public interface SortType {
        int DEFAULT = 0;
        int ALPHABETICALLY = 1;
        int ENDINGSOON = 2;
    }
}
