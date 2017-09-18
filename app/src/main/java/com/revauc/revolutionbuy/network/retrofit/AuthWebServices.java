package com.revauc.revolutionbuy.network.retrofit;


import com.revauc.revolutionbuy.BuildConfig;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.request.auth.SignUpRequest;
import com.revauc.revolutionbuy.network.response.LoginResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * This interface has collection of apis used in Project . Retrofit turns your HTTP API into a Java interface.
 * <p>
 * A request URL can be updated dynamically using replacement blocks and parameters on the method. A replacement block is an alphanumeric string surrounded by { and }. A corresponding parameter must be annotated with @Path using the same string.
 *
 * @GET("group/{id}/users") Call<List<User>> groupList(@Path("id") int groupId);
 * <p>
 * <p>
 * Query parameters can also be added.
 * @GET("group/{id}/users") Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);
 * <p>
 * For complex query parameter combinations a Map can be used.
 * @GET("group/{id}/users") Call<List<User>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);
 * <p>
 * <p>
 * REQUEST BODY
 * An object can be specified for use as an HTTP request body with the @Body annotation.
 * @POST("users/new") Call<User> createUser(@Body User user);
 * The object will also be converted using a converter specified on the Retrofit instance. If no converter is added, only RequestBody can be used.
 */

public interface AuthWebServices {

    String QUERY_CURRENT_PAGE = "currentPage";
    String QUERY_CURRENT_SIZE = "currentSize";

    String FB_LOGIN = BuildConfig.BASE_URL + "users/fb-sign-up";
    String REGISTER = BuildConfig.BASE_URL + "users/sign-up";
    String LOGIN = BuildConfig.BASE_URL + "users/sign-in";
    String TERMS = BuildConfig.BASE_URL + "terms-conditions";
    String PRIVACY = BuildConfig.BASE_URL + "privacy-policies";

//    @POST(FB_LOGIN)
//    Observable<LoginResponse> loginUsingFacebook(@Body FBLoginRequest params);
//
    @POST(REGISTER)
    Observable<LoginResponse> registerUser(@Body SignUpRequest params);

    @POST(LOGIN)
    Observable<LoginResponse> loginUser(@Body SignUpRequest params);
//
//    @POST(FORGOT_PASSWORD)
//    Observable<BaseResponse> forgotPassword(@Body ForgotPasswordRequest params);
//
//    @POST(CHANGE_PASSWORD)
//    Observable<BaseResponse> changePassword(@Body ChangePasswordRequest params);
//
//    @DELETE(LOGOUT)
//    Observable<LogoutResponse> logout();
//
//    @POST(CONTEST_DETAILS)
//    Observable<ContestDetailResponse> getContestDetails(@Body ContestDetailRequest contestDetailRequest);
//
//    @POST(CONTEST_DETAILS)
//    Observable<LiveContestDetailResponse> getLiveContestDetails(@Body ContestDetailRequest contestDetailRequest);
//
//    @POST(CONTEST_DETAILS)
//    Observable<HistoryContestDetailResponse> getHistoryContestDetails(@Body ContestDetailRequest contestDetailRequest);
//
//    @POST(CONTEST_LISTING)
//    Observable<ContestResponse> getcontestListing(@Body ContestRequest params);
//
//    @POST(CONTEST_HISTORY_LISTING)
//    Observable<ContestHistoryResponse> getcontestHistoryListing(@Body LiveContestRequest params);
//
//    @POST(CONTEST_LIVE_LISTING)
//    Observable<ContestLiveResponse> getcontestLiveListing(@Body LiveContestRequest params);
//
//    @POST(CONTEST_UPCOMING_LISTING)
//    Observable<ContestResponse> getcontestUpcomingListing(@Body ContestRequest params);
//
//    @POST(AVAILABLE_PROPS)
//    Observable<PropsResponse> getcontestProps(@Body PicksRequest params);
//
//    @POST(USER_IMPORTED_CONTACTS)
//    Observable<PropsResponse> getImportedcontestProps(@Body PicksImportRequest params);
//
//    @POST(SELECTED_PROPS)
//    Observable<PropsResponse> getSelectedProps(@Body PicksRequest params);
//
//    @POST(ENTER_CONTEST)
//    Observable<ContestEnteredResponse> enterContest(@Path("enterType") Integer value , @Body EnterContest params);
//
//    @POST(ENTRANTS_LIST)
//    Observable<EntrantsResponse> getEntrantsListing(@Body EntrantsRequest params);
//
//    @POST(LIVE_ENTRANTS_LIST)
//    Observable<LiveEntrantsResponse> getLiveEntrantsListing(@Body EntrantsRequest params);
//
//
//    @POST(FOLLOW_USER)
//    Observable<BaseResponse> followEntrant(@Body EntrantsFollowRequest params);
//
//    @POST(WITHDRAW_CONTEST)
//    Observable<BaseResponse> withdrawFromContest(@Body WithdrawRequest params);
//
//    @POST(PLAYER_SEASON_STATS)
//    Observable<PlayerSeasonResponse> getPlayerSeasonStats(@Body PlayerStatsRequest params);
//
//    @POST(PLAYER_MATCH_STATS)
//    Observable<PlayerMatchesResponse> getPlayerMatchStats(@Body PlayerStatsRequest params);
//
//    @POST(INVITE_FRIENDS)
//    Observable<BaseResponse> inviteFriends(@Body InviteFriendRequest params);
//
//    @POST(NOTIFICATION_LIST)
//    Observable<NotificationResponse> getNotificationListing(@Body NotificationRequest params);
//
//    @POST(READ_NOTIFICATION)
//    Observable<BaseResponse> readNotification(@Body ReadNotificationRequest params);
//
//    @POST(NOTIFICATION_SETTINGS)
//    Observable<BaseResponse> updateNotificationSettings(@Body NotificationSettingRequest params);
//
//    @POST(IMPORT_PICKS)
//    Observable<ContestImportResponse> importPicks(@Body ContestDetailRequest params);
//
//    @POST(APPLY_SIMILAR)
//    Observable<BaseResponse> applySimilar(@Body ApplySilmilarRequest params);
//
//    @POST(UPDATE_DEVICE_TOKEN)
//    Observable<BaseResponse> updateToken(@Body UpdateDeviceTokenRequest params);
//
//    @GET(GET_ICE_DESCRIPTION)
//    Observable<ICEDescResponse> getICEDescription();
//
//    @GET(GET_PRIVACY_URL)
//    Observable<TermsResponse> getPrivacyUrl();
//
//    @GET(GET_HOW_TO_PLAY)
//    Observable<TermsResponse> getHowToPlay();
//
//    @POST(UPDATE_TERMS_URL)
//    Observable<BaseResponse> updateTermsVersion(@Body UpdateTermsVersionRequest version);
//
//    @GET(EXPORT_CSV)
//    Observable<BaseResponse> exportCsv(@Query("contestid") Integer contestId);
//
//    @GET(GET_TERMS_VERSION_URL)
//    Observable<TermsVersionResponse> getTermsVersion();
//
//    @GET(CONTEST_PICK_LIVE)
//    Observable<LivePropsResponse> pickLive(@Query("contestid") Integer contestId,@Query("userContestId") Integer userContestId);
//
//    @GET(HEAD_TO_HEAD_LIVE)
//    Observable<HeadToHeadResponse> headToHeadLive(@Query("contestid") Integer contestId,@Query("userContestId") Integer userContestId, @Query("opponentid") Integer opponentId, @Query("opponentUserContestId") Integer opponentUserContestId);
//
//    @GET(HEAD_TO_HEAD_PROPS)
//    Observable<PropsDeltaResponse> headToHeadProps(@Query("contestid") Integer contestId,@Query("userContestId") Integer userContestId, @Query("opponentid") Integer opponentId, @Query("opponentUserContestId") Integer opponentUserContestId);
}
