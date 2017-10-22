package com.revauc.revolutionbuy.network.retrofit;


import com.revauc.revolutionbuy.BuildConfig;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.request.auth.ChangePasswordRequest;
import com.revauc.revolutionbuy.network.request.auth.ForgotPasswordRequest;
import com.revauc.revolutionbuy.network.request.auth.MobilePinRequest;
import com.revauc.revolutionbuy.network.request.auth.MobileVerifyRequest;
import com.revauc.revolutionbuy.network.request.auth.ProductSearchRequest;
import com.revauc.revolutionbuy.network.request.auth.SignUpRequest;
import com.revauc.revolutionbuy.network.request.auth.SocialSignUpRequest;
import com.revauc.revolutionbuy.network.response.buyer.CategoriesResponse;
import com.revauc.revolutionbuy.network.response.buyer.WishlistResponse;
import com.revauc.revolutionbuy.network.response.profile.CityResponse;
import com.revauc.revolutionbuy.network.response.profile.CountryResponse;
import com.revauc.revolutionbuy.network.response.LoginResponse;
import com.revauc.revolutionbuy.network.response.profile.StateResponse;
import com.revauc.revolutionbuy.network.response.seller.SellerProductsResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface AuthWebServices {

    String QUERY_CURRENT_PAGE = "currentPage";
    String QUERY_CURRENT_SIZE = "currentSize";

    String FB_LOGIN = BuildConfig.BASE_URL + "users/fb-sign-up";
    String REGISTER = BuildConfig.BASE_URL + "users/sign-up";
    String LOGIN = BuildConfig.BASE_URL + "users/sign-in";
    String FORGOT_PASSWORD = BuildConfig.BASE_URL + "users/forget-password";
    String GET_COUNTRIES = BuildConfig.BASE_URL + "countries";
    String GET_STATES = BuildConfig.BASE_URL + "states";
    String GET_CITIES = BuildConfig.BASE_URL + "cities";
    String MOBILE_PIN = BuildConfig.BASE_URL + "users/mobile-pin";
    String ADD_PROFILE = BuildConfig.BASE_URL + "users/add-profile";
    String EDIT_PROFILE = BuildConfig.BASE_URL + "users/edit-profile";
    String LOGOUT = BuildConfig.BASE_URL + "users/logout";
    String CHANGE_PASSWORD = BuildConfig.BASE_URL + "users/change-password";
    String GET_CATEGORIES = BuildConfig.BASE_URL + "categories";
    String GET_BUYER_WISHLIST = BuildConfig.BASE_URL + "buyer-wishlist-products";
    String PRODUCTS_LIST = BuildConfig.BASE_URL + "products/search";
    String ADD_BUYER_PRODUCT = BuildConfig.BASE_URL + "products/buyer-products";
    String DELETE_BUYER_PRODUCT = BuildConfig.BASE_URL + "products/";
    String VERIFY_MOBILE_NUMBER = BuildConfig.BASE_URL + "users/verify-mobile";





    @POST(FB_LOGIN)
    Observable<LoginResponse> loginUsingFacebook(@Body SocialSignUpRequest params);
//
    @POST(REGISTER)
    Observable<LoginResponse> registerUser(@Body SignUpRequest params);

    @POST(LOGIN)
    Observable<LoginResponse> loginUser(@Body SignUpRequest params);

    @POST(CHANGE_PASSWORD)
    Observable<BaseResponse> changePassword(@Body ChangePasswordRequest params);

    @POST(FORGOT_PASSWORD)
    Observable<BaseResponse> forgotPassword(@Body ForgotPasswordRequest params);

    @POST(MOBILE_PIN)
    Observable<BaseResponse> generateMobilePin(@Body MobilePinRequest params);

    @POST(VERIFY_MOBILE_NUMBER)
    Observable<BaseResponse> verifyMobileChangePin(@Body MobileVerifyRequest params);

    @GET(GET_COUNTRIES)
    Observable<CountryResponse> getCountries();

    @GET(GET_CATEGORIES)
    Observable<CategoriesResponse> getCategories();

    @GET(GET_STATES)
    Observable<StateResponse> getStates(@Query("countryId") Integer countryId);

    @DELETE(DELETE_BUYER_PRODUCT+"{id}")
    Observable<BaseResponse> deleteBuyerProduct(@Path("id") int productId);

    @GET(GET_BUYER_WISHLIST)
    Observable<WishlistResponse> getBuyerWishlist(@Query("offset") Integer offset, @Query("limit") Integer limit);

    @POST(PRODUCTS_LIST)
    Observable<SellerProductsResponse> getSellerProductsListing(@Body ProductSearchRequest params);

    @Multipart
    @POST(ADD_PROFILE)
    Observable<LoginResponse> uploadFormData(@PartMap() Map<String, RequestBody> partMap);

    @Multipart
    @POST(ADD_PROFILE)
    Observable<LoginResponse> uploadFormData(@PartMap() Map<String, RequestBody> partMap,@Part MultipartBody.Part file);

    @Multipart
    @POST(EDIT_PROFILE)
    Observable<LoginResponse> editProfile(@PartMap() Map<String, RequestBody> partMap,@Part MultipartBody.Part file);

    @Multipart
    @POST(EDIT_PROFILE)
    Observable<LoginResponse> editProfile(@PartMap() Map<String, RequestBody> partMap);

    @Multipart
    @POST(ADD_BUYER_PRODUCT)
    Observable<BaseResponse> addBuyerProduct(@PartMap() Map<String, RequestBody> partMap);

    @Multipart
    @POST(ADD_BUYER_PRODUCT)
    Observable<BaseResponse> addBuyerProduct(@PartMap() Map<String, RequestBody> partMap,@Part MultipartBody.Part primary);

    @Multipart
    @POST(ADD_BUYER_PRODUCT)
    Observable<BaseResponse> addBuyerProduct(@PartMap() Map<String, RequestBody> partMap,@Part MultipartBody.Part primary,@Part MultipartBody.Part one);

    @Multipart
    @POST(ADD_BUYER_PRODUCT)
    Observable<BaseResponse> addBuyerProduct(@PartMap() Map<String, RequestBody> partMap,@Part MultipartBody.Part primary,@Part MultipartBody.Part one,@Part MultipartBody.Part two);

    @GET(GET_CITIES)
    Observable<CityResponse> getCities(@Query("countryId") Integer countryId, @Query("stateId") Integer stateId);

//    @POST(CHANGE_PASSWORD)
//    Observable<BaseResponse> changePassword(@Body ChangePasswordRequest params);
//
    @GET(LOGOUT)
    Observable<BaseResponse> logout();
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
