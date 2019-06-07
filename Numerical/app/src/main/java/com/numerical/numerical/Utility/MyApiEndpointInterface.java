package com.numerical.numerical.Utility;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface MyApiEndpointInterface {


    @GET
    Call<ResponseBody> latest(@Url String value);

    @POST(Const.ENDPOINT.LOGIN_URL)
    Call<JsonObject> user_login(@Body JsonObject jsonObject);

    @POST(Const.ENDPOINT.Social_LOGIN_URL)
    Call<JsonObject> Social_LOGIN_URL(@Body JsonObject jsonObject);

    @POST(Const.ENDPOINT.Signup)
    Call<JsonObject> Signup(@Body JsonObject jsonObject);
//Const.ENDPOINT.Comment + example.getId() + "/numeron/"+numeron_id+"/comment"
    @POST(Const.ENDPOINT.Comment)
    Call<JsonObject> SubmitComment(@Body JsonObject jsonObject);

    /*@POST(Const.ENDPOINT.LikeNumerouns)
    Call<JsonObject> LikeNumerouns(@Body JsonObject jsonObject);*/

    @PUT
    Call<ResponseBody> LikeNumerouns(@Url String value);

    @PUT
    Call<ResponseBody> FollowNumerouns(@Url String value);

    @GET
    Call<ResponseBody> Comments(@Url String value);
    /*@Multipart
    @POST(Const.ENDPOINT.AddCollege)
    Call<ResponseBody> AddCollege(@Query("user_id") String user_id, @Query("university_id") String university_id, @Query("college_name") String college_name, @Query("college_type") String college_type, @Query("email") String email, @Query("user_contact") String contact, @Query("address") String address, @Query("state") String state, @Query("city") String city, @Query("latitude") String latitude, @Query("longitude") String longitude, @Query("website") String website, @Query("department_ids") String department_ids, @Query("course_ids") String course_ids, @Query("district_id") String district_id, @Query("college_time") String college_time, @Part MultipartBody.Part file);

 */

}
