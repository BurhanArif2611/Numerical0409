package com.numerical.numerical.Firebase;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.JsonObject;
import com.numerical.numerical.Utility.SavedData;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    private String refreshedToken;

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat
        Log.e("", "Refreshed token:" + refreshedToken);
        SavedData.saveTokan(refreshedToken);
       /* if (UserProfileHelper.getInstance().getUserProfileModel().size()>0){
            UpdateFCMFromServer(refreshedToken);
        }*/
    }


   /* private void UpdateFCMFromServer(String refreshedToken) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
        jsonObject.addProperty("device_id", refreshedToken);
        MyApiEndpointInterface apiService = ApiClient.getClient().create(MyApiEndpointInterface.class);
        Call<JsonObject> call = apiService.UpdateFcm(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ErrorMessage.E("Error" + t.toString());

            }
        });

    }*/


}
