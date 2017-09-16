package com.revauc.revolutionbuy.network.retrofit;


import com.revauc.revolutionbuy.BuildConfig;


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

    String FB_LOGIN = BuildConfig.BASE_URL + "fb/register";
    String REGISTER = BuildConfig.BASE_URL + "signup";
    String LOGIN = BuildConfig.BASE_URL + "login";
    String FORGOT_PASSWORD = BuildConfig.BASE_URL + "send-reset-password-link";
    String CHANGE_PASSWORD = BuildConfig.BASE_URL + "/admin/change-password";

    String LOGOUT = BuildConfig.BASE_URL + "logout";
    String CONTEST_LISTING = BuildConfig.BASE_URL + "contests/showContests";
    String CONTEST_HISTORY_LISTING = BuildConfig.BASE_URL + "contests/get-my-contest-history";
    String CONTEST_LIVE_LISTING = BuildConfig.BASE_URL + "contests/live";
    String CONTEST_UPCOMING_LISTING = BuildConfig.BASE_URL + "contests/upcoming";

    String CONTEST_DETAILS = BuildConfig.BASE_URL + "contests/contestDetails";
    String AVAILABLE_PROPS = BuildConfig.BASE_URL + "contests/availableProps";
    String ENTER_CONTEST = BuildConfig.BASE_URL + "contests/enter/{enterType}";
    String WITHDRAW_CONTEST = BuildConfig.BASE_URL + "contests/withdraw-contest";
    String ENTRANTS_LIST = BuildConfig.BASE_URL + "userContests/entrantsListByContest";
    String LIVE_ENTRANTS_LIST = BuildConfig.BASE_URL + "userContests/entrantsListByLiveContest";
    String SELECTED_PROPS = BuildConfig.BASE_URL + "userContests/selectedProps";
    String FOLLOW_USER = BuildConfig.BASE_URL + "userContests/userFollowsOtherUser";
    String PLAYER_SEASON_STATS = BuildConfig.BASE_URL + "userContests/playerSeasonStats";
    String PLAYER_MATCH_STATS = BuildConfig.BASE_URL + "userContests/playerEventsStats";

    String NOTIFICATION_LIST = BuildConfig.BASE_URL + "notification/notification-listing";
    String READ_NOTIFICATION = BuildConfig.BASE_URL + "notification/read-notification";
    String NOTIFICATION_SETTINGS = BuildConfig.BASE_URL + "notification/notification-setting";
    String INVITE_FRIENDS = BuildConfig.BASE_URL + "invitee/user-invitee";
    String IMPORT_PICKS = BuildConfig.BASE_URL + "prop/importPicks";
    String APPLY_SIMILAR = BuildConfig.BASE_URL + "userContests/apply-user-picks-to-similar-contests";
    String CONTEST_PICK_LIVE = BuildConfig.BASE_URL + "contests/get-user-picks-live-contest";
    String HEAD_TO_HEAD_LIVE = BuildConfig.BASE_URL + "contests/get-head-to-head-view-user-picks";
    String HEAD_TO_HEAD_PROPS = BuildConfig.BASE_URL + "contests/get-live-props-data";
    String USER_IMPORTED_CONTACTS = BuildConfig.BASE_URL + "userContests/userImportedContests";
    String UPDATE_DEVICE_TOKEN = BuildConfig.BASE_URL + "updateDeviceToken";
    String GET_ICE_DESCRIPTION = BuildConfig.BASE_URL + "adminContest/get-ice-prop-description";
    String GET_TERMS_URL = BuildConfig.BASE_URL + "adminContest/get-terms-and-condition";
    String GET_PRIVACY_URL = BuildConfig.BASE_URL + "adminContest/get-privacy-policy";
    String GET_HOW_TO_PLAY = BuildConfig.BASE_URL + "adminContest/get-how-to-play";

    String GET_TERMS_VERSION_URL = BuildConfig.BASE_URL + "users/get-terms-condition-current-version";
    String UPDATE_TERMS_URL = BuildConfig.BASE_URL + "users/update-terms-condition-version";
    String EXPORT_CSV = BuildConfig.BASE_URL + "contests/get-csv-by-contest";



//    @POST(FB_LOGIN)
//    Observable<LoginResponse> loginUsingFacebook(@Body FBLoginRequest params);
//
//    @POST(REGISTER)
//    Observable<LoginResponse> registerUser(@Body SignUpRequest params);
//
//    @POST(LOGIN)
//    Observable<LoginResponse> loginUser(@Body LoginRequest params);
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
//    @GET(GET_TERMS_URL)
//    Observable<TermsResponse> getTermsUrl();
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
