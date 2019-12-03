package com.pixelmarketo.nrh.utility;


import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LoadInterface {


    @POST("register_user")
    @FormUrlEncoded
    Call<ResponseBody> userRagistration(@Field(value = "fullname") String first_name, @Field(value = "contact") String contact, @Field(value = "state") String state, @Field(value = "district") String district, @Field(value = "tehsil") String tehsil, @Field(value = "city") String city, @Field(value = "address") String address, @Field(value = "pincode") String pincode, @Field(value = "password") String password);


    @GET("ServiceList")
    Call<ResponseBody> ServiceList();

    @GET("get_all_product")
    Call<ResponseBody> get_all_product();

    @POST("front_page")
    @FormUrlEncoded
    Call<ResponseBody> front_page(@Field(value = "token") String token);

    @POST("approve_bid_list")
    @FormUrlEncoded
    Call<ResponseBody> approve_bid_list(@Field(value = "token") String token);

    @POST("filter_product_name")
    @FormUrlEncoded
    Call<ResponseBody> filter_product_name(@Field(value = "name") String name);

    @POST("service_by_vendor_id")
    @FormUrlEncoded
    Call<ResponseBody> service_by_vendor_id(@Field(value = "token") String token);

    @POST("payment_service_admin")
    @FormUrlEncoded
    Call<ResponseBody> payment_service_admin(@Field(value = "id") String id,@Field(value = "tranjestion_id") String tranjestion_id,@Field(value = "payment_method") String payment_method,@Field(value = "payment") String payment);

    @POST("request_by_vendor_id")
    @FormUrlEncoded
    Call<ResponseBody> request_by_vendor_id(@Field(value = "token") String token,@Field(value = "type") String type,@Field(value = "status") String status,@Field(value = "service_sub_type") String service_sub_type);


    @POST("approve_bid_list")
    @FormUrlEncoded
    Call<ResponseBody> approve_bid_list(@Field(value = "token") String token,@Field(value = "service_type") String type,@Field(value = "service_sub_type") String service_sub_type);

    @POST("get_subcategory")
    @FormUrlEncoded
    Call<ResponseBody> subcategory(@Field(value = "category_id") String category_id);

    @POST("getproductbycat")
    @FormUrlEncoded
    Call<ResponseBody> getproductbycat(@Field(value = "cat_id") String token);

    @POST("service_bid_user")
    @FormUrlEncoded
    Call<ResponseBody> service_bid_user(@Field(value = "token") String token);

    @POST("service_bid")
    @FormUrlEncoded
    Call<ResponseBody> service_bid(@Field(value = "token") String token,@Field(value = "service_id") String service_id);

    @POST("filter_product")
    @FormUrlEncoded
    Call<ResponseBody> filter_product(@Field(value = "token") String token, @Field(value = "company_id") String company_id, @Field(value = "model_id") String model_id, @Field(value = "category_id") String category_id, @Field(value = "year") String year);


    @POST("get_model")
    @FormUrlEncoded
    Call<ResponseBody> get_model(@Field(value = "company_id") String company_id);

    @POST("service_cart")
    @FormUrlEncoded
    Call<ResponseBody> service_cart(@Field(value = "auth_token") String auth_token, @Field(value = "package_id") String package_id, @Field(value = "company_id") String company_id, @Field(value = "model_id") String model_id, @Field(value = "city_id") String city_id);

    @GET("getcity")
    Call<ResponseBody> getcity();

    @GET("getservicetype")
    Call<ResponseBody> getservicetype();

    @GET("getcompany")
    Call<ResponseBody> getcompany();

    @GET("assigncompleteServiceList")
    Call<ResponseBody> assigncompleteServiceList();

    @GET("sale_status_history")
    Call<ResponseBody> sale_status_history();

    @POST("login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field(value = "user_contact") String contact ,@Field(value = "user_password") String password  , @Field(value = "device_id") String device_id);

    @POST("vendor_login")
    @FormUrlEncoded
    Call<ResponseBody> vendor_login(@Field(value = "user_contact") String contact ,@Field(value = "user_password") String password  , @Field(value = "device_id") String device_id);

    @POST("conformation_payment")
    @FormUrlEncoded
    Call<ResponseBody> conformation_payment(@Field(value = "token") String token ,@Field(value = "tranjection_id") String tranjection_id,@Field(value = "payment_method") String payment_method);

    @POST("forgat_pass")
    @FormUrlEncoded
    Call<ResponseBody> forgat_pass(@Field(value = "email") String email);

    @POST("productDetails")
    @FormUrlEncoded
    Call<ResponseBody> productDetails(@Field(value = "token") String token, @Field(value = "product_id") String product_id);

    @POST("Get_address")
    @FormUrlEncoded
    Call<ResponseBody> Get_address(@Field(value = "auth_token") String token);

    @POST("add_address")
    @FormUrlEncoded
    Call<ResponseBody> add_address(@Field(value = "address_var") String address_var, @Field(value = "latitude_var") String latitude_var, @Field(value = "longitude_var") String longitude_var, @Field(value = "auth_token_var") String auth_token_var);

    @POST("editaddress")
    @FormUrlEncoded
    Call<ResponseBody> editaddress(@Field(value = "address_id") String address_id, @Field(value = "address_var") String address_var, @Field(value = "latitude_var") String latitude_var, @Field(value = "longitude_var") String longitude_var, @Field(value = "auth_token_var") String auth_token_var);

    @POST("deleteAddress")
    @FormUrlEncoded
    Call<ResponseBody> deleteAddress(@Field(value = "address_id") String address_id);

    @POST("add_to_cart")
    @FormUrlEncoded
    Call<ResponseBody> add_to_cart(@Field(value = "auth_token") String auth_token, @Field(value = "product_id") String product_id, @Field(value = "quantity") String quantity);

    @POST("getCartByUserId")
    @FormUrlEncoded
    Call<ResponseBody> getCartByUserId(@Field(value = "auth_token") String auth_token);


    @POST("getservicecart")
    @FormUrlEncoded
    Call<ResponseBody> getservicecart(@Field(value = "auth_token") String auth_token);

    @POST("EmptyCart")
    @FormUrlEncoded
    Call<ResponseBody> EmptyCart(@Field(value = "auth_token") String auth_token);

    @POST("EmptyServiceCart")
    @FormUrlEncoded
    Call<ResponseBody> EmptyServiceCart(@Field(value = "auth_token") String auth_token);

    @POST("myOrder")
    @FormUrlEncoded
    Call<ResponseBody> myOrder(@Field(value = "auth_token") String auth_token);

    @POST("reviewProduct")
    @FormUrlEncoded
    Call<ResponseBody> reviewProduct(@Field(value = "auth_token") String auth_token, @Field(value = "product_id") String product_id, @Field(value = "review") String review, @Field(value = "rating") String rating);

    @POST("RemoveToCart")
    @FormUrlEncoded
    Call<ResponseBody> RemoveToCart(@Field(value = "auth_token") String auth_token, @Field(value = "product_id") String product_id);

    @POST("RemoveToServiceCart")
    @FormUrlEncoded
    Call<ResponseBody> RemoveToServiceCart(@Field(value = "auth_token") String auth_token, @Field(value = "service_id") String service_id);

    @POST("confirmOrder")
    @FormUrlEncoded
    Call<ResponseBody> confirmOrder(@Field(value = "auth_token") String auth_token, @Field(value = "payment_method") String payment_method, @Field(value = "transaction_id") String transaction_id, @Field(value = "address") String address, @Field(value = "delivery_time") String delivery_time);

    @POST("service_order_confirm")
    @FormUrlEncoded
    Call<ResponseBody> service_order_confirm(@Field(value = "auth_token") String auth_token, @Field(value = "payment_method") String payment_method, @Field(value = "transaction_id") String transaction_id, @Field(value = "address") String address, @Field(value = "date") String date, @Field(value = "time") String time, @Field(value = "select_mode") String select_mode);


    @POST("getservicebyid")
    @FormUrlEncoded
    Call<ResponseBody> getservicebyid(@Field(value = "city_id") String city_id, @Field(value = "model_id") String model_id, @Field(value = "company_id") String company_id, @Field(value = "service_cat") String service_cat);

    @POST("front_car_service")
    @FormUrlEncoded
    Call<ResponseBody> front_car_service(@Field(value = "city_id") String city_id, @Field(value = "model_id") String model_id, @Field(value = "company_id") String company_id);

    @Multipart
    @POST("update_user")
    Call<ResponseBody> UpdateProfile(@Part("token") RequestBody token, @Part("fullname") RequestBody first_name, @Part("contact") RequestBody contact, @Part("email") RequestBody email, @Part MultipartBody.Part part);

    @POST("update_user")
    @FormUrlEncoded
    Call<ResponseBody> UpdateProfile_WithoutImage(@Field(value ="token") String token, @Field(value ="fullname") String first_name, @Field(value ="contact") String contact, @Field(value ="email") String email);

    @Multipart
    @POST("service_request")
    Call<ResponseBody> service_request(@Query("token") String token, @Query("service_type") String service_type, @Query("type") String type, @Query("event_name") String event_name, @Query("from_date") String from_date, @Query("to_date") String to_date, @Query("city") String city, @Query("tehsil") String tehsil, @Query("no_of_member") String no_of_member, @Query("no_of_item") String no_of_item, @Part MultipartBody.Part[] file1);

    @Multipart
    @POST("register_vendor")
    Call<ResponseBody> register_vendor(@Query("fullname") String fullname, @Query("contact") String contact, @Query("state") String state, @Query("district") String district, @Query("tehsil") String tehsil, @Query("city") String city, @Query("address") String address, @Query("pincode") String pincode, @Query("password") String password, @Query("service_type[]") ArrayList<String> service_type, @Part MultipartBody.Part file1);

    //token,service_type,event_name,event_date,type,from_date,to_date,vehical_type,no_of_days,singer_dancer,pick_up_time,no_of_watercan,form_place,to_place,no_of_day,no_of_baggi_horse,no_of_member,no_of_item,vaters,decoretion_type,cantenscantens,no_of_dholno_of_dhol,service_imageservice_image
    @POST("service_request")
    @FormUrlEncoded
    Call<ResponseBody> service_request_WithoutImage(@Field(value ="token") String token, @Field(value ="service_type") String service_type, @Field(value ="type") String type, @Field(value ="event_name") String event_name, @Field(value ="event_date") String event_date, @Field(value ="city") String city, @Field(value ="tehsil") String tehsil, @Field(value ="no_of_dhol") String no_of_dhol, @Field(value ="no_of_watercan") String no_of_watercan, @Field(value ="singer_dancer") String singer_dancer, @Field(value ="no_of_member") String no_of_member, @Field(value ="no_of_item") String no_of_item, @Field(value ="decoretion_type") String decoretion_type);

    @POST("service_request")
    @FormUrlEncoded
    Call<ResponseBody> service_request_WithoutImage(@Field(value ="token") String token, @Field(value ="service_type") String service_type, @Field(value ="type") String type, @Field(value ="event_name") String event_name, @Field(value ="event_date") String event_date, @Field(value ="city") String city, @Field(value ="tehsil") String tehsil, @Field(value ="no_of_dhol") String no_of_dhol, @Field(value ="no_of_watercan") String no_of_watercan, @Field(value ="singer_dancer") String singer_dancer, @Field(value ="no_of_member") String no_of_member, @Field(value ="no_of_item") String no_of_item, @Field(value ="cantens") String cantens, @Field(value ="form_place") String form_place, @Field(value ="to_place") String to_place, @Field(value ="pick_up_time") String pick_up_time, @Field(value ="vehical_type") String vehical_type, @Field(value ="no_of_baggi_horse") String no_of_baggi_horse, @Field(value ="no_of_days") String no_of_day,@Query("to_date") String to_date,@Query("no_horse") String no_horse);

    @POST("service_request")
    @FormUrlEncoded
    Call<ResponseBody> service_request_WithoutImage(@Field(value ="token") String token, @Field(value ="service_type") String service_type, @Field(value ="type") String type, @Field(value ="event_name") String event_name, @Field(value ="event_date") String event_date, @Field(value ="city") String city, @Field(value ="tehsil") String tehsil, @Field(value ="no_of_dhol") String no_of_dhol, @Field(value ="no_of_watercan") String no_of_watercan, @Field(value ="singer_dancer") String singer_dancer, @Field(value ="no_of_member") String no_of_member, @Field(value ="no_of_item") String no_of_item, @Field(value ="cantens") String cantens, @Field(value ="form_place") String form_place, @Field(value ="to_place") String to_place, @Field(value ="pick_up_time") String pick_up_time, @Field(value ="vehical_type") String vehical_type, @Field(value ="no_of_baggi_horse") String no_of_baggi_horse, @Field(value ="no_of_day") String no_of_day, @Field(value ="drone_crean") String drone_crean, @Field(value ="pre_vid_shutting") String pre_vid_shutting, @Field(value ="led_wall") String led_wall, @Field(value ="vid_cd_hours") String vid_cd_hours);


    @POST("update_bid_by_vendorid")
    @FormUrlEncoded
    Call<ResponseBody> update_bid_by_vendorid(@Field(value ="id") String id, @Field(value ="bid_amount") String bid_amount, @Field(value ="title") String title);

    @POST("approve_bid_by_user")
    @FormUrlEncoded
    Call<ResponseBody> approve_bid_by_user(@Field(value ="id") String id);

    @POST("delete_history")
    @FormUrlEncoded
    Call<ResponseBody> delete_history(@Field(value ="id") String id);

}