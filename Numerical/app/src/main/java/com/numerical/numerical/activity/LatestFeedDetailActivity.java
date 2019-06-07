package com.numerical.numerical.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.numerical.numerical.Models.LatestFeed.Example;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ApiClient;
import com.numerical.numerical.Utility.Const;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.Utility.NetworkUtil;
import com.numerical.numerical.Utility.SavedData;
import com.numerical.numerical.adapters.CommentAdapter;
import com.numerical.numerical.database.UserProfileHelper;
import com.numerical.numerical.fragments.ListingViewFragment;
import com.numerical.numerical.fragments.StandredViewFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestFeedDetailActivity extends BaseActivity {


    @BindView(R.id.authore_tv)
    TextView authoreTv;
    @BindView(R.id.tilte_tv)
    TextView tilteTv;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.like_img)
    ImageView likeImg;
    @BindView(R.id.share_img)
    ImageView shareImg;
    @BindView(R.id.follow_byn_img)
    ImageView followBynImg;
    @BindView(R.id.action_menu_img)
    ImageView actionMenuImg;
    @BindView(R.id.back_btn_tv)
    TextView backBtnTv;
    @BindView(R.id.comment_rv)
    RecyclerView commentRv;
    @BindView(R.id.comment_relativelayout)
    RelativeLayout commentRelativelayout;
    @BindView(R.id.bottom_cardview)
    CardView bottomCardview;
    @BindView(R.id.write_comment_etv)
    EditText writeCommentEtv;

    private FragmentTransaction mFragmentTransaction;
    FragmentManager detailsFragment1 = getSupportFragmentManager();
    private View view_settings;
    private String fragmentTitle = "Listview";
    private Example example;
    private Intent intent;


    @Override
    protected int getContentResId() {
        return R.layout.activity_latest_feed_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        try {
            example = (Example) getIntent().getSerializableExtra("ItemData");
            tilteTv.setText(example.getTitle());
            authoreTv.setText(example.getUser().getDisplayName());
            if (getIntent().getStringExtra("Name").equals("")) {
                if (example.getIsCollection()) {
                    tilteTv.setVisibility(View.VISIBLE);
                    setToolbarWithBackButton("Collection");
                } else {
                    tilteTv.setVisibility(View.GONE);
                    setToolbarWithBackButton("Numerons");
                }
            } else {
                setToolbarWithBackButton(getIntent().getStringExtra("Name"));
                if (example.getIsCollection()) {
                    tilteTv.setVisibility(View.VISIBLE);
                } else {
                    tilteTv.setVisibility(View.GONE);
                }
            }
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy - hh:mm a");
            Date d = null;
            try {
                d = input.parse(example.getLastModified());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String formatted = output.format(d);
            dateTv.setText(formatted);
            ListingViewFragment presonalDetailsFragment1 = new ListingViewFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("ItemData", example);
            presonalDetailsFragment1.setArguments(bundle);
            mFragmentTransaction = detailsFragment1.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
            mFragmentTransaction.commit();

        } catch (Exception e) {
        }
        getfollowOnServer();
        backBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                commentRelativelayout.startAnimation(animSlideUp);
                commentRelativelayout.setVisibility(View.GONE);
                bottomCardview.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (example.getIsCollection()) {
            getMenuInflater().inflate(R.menu.list_view, menu);
            view_settings = findViewById(R.id.view_settings);
        } else {
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        view_settings = findViewById(R.id.view_settings);

        //noinspection SimplifiableIfStatement
        if (id == R.id.view_settings) {
            if (fragmentTitle.equals("Standerd")) {
                ListingViewFragment presonalDetailsFragment1 = new ListingViewFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ItemData", example);
                presonalDetailsFragment1.setArguments(bundle);
                mFragmentTransaction = detailsFragment1.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
                mFragmentTransaction.commit();
            } else if (fragmentTitle.equals("Listview")) {
                StandredViewFragment presonalDetailsFragment1 = new StandredViewFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ItemData", example);
                presonalDetailsFragment1.setArguments(bundle);
                mFragmentTransaction = detailsFragment1.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
                mFragmentTransaction.commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void launchFragmentTitle(String fragemntName) {
        fragmentTitle = fragemntName;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        if (example.getIsCollection()) {
            if (fragmentTitle.equals("Listview")) {
                menu.findItem(R.id.view_settings).setVisible(true).setIcon(getResources().getDrawable(R.drawable.ic_list_view));
            } else if (fragmentTitle.equals("Standerd")) {
                menu.findItem(R.id.view_settings).setVisible(true).setIcon(getResources().getDrawable(R.drawable.ic_standard_view));

            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void share() {
        String sAux = " Numerical App\n\n";
        if (example.getIsCollection()) {
            sAux = sAux + "https://numerical.co.in/numerons/collection/" + example.getId();
        } else {
            sAux = sAux + "https://numerical.co.in/numerons/numeron/" + example.getId();

        }
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Numerical");
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    @OnClick({R.id.like_img, R.id.share_img, R.id.authore_tv, R.id.follow_byn_img, R.id.action_menu_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.like_img:
                sendMessage();
                break;
            case R.id.share_img:
                share();
                break;
            case R.id.follow_byn_img:
                ErrorMessage.E("Toka" + SavedData.getTokan());
                followOnServer();
                break;
            case R.id.action_menu_img:
                ErrorMessage.E("Action Size" + example.getActions().size());
                if (example.getActions().size() > 0) {
                    showPopup(view);
                } else {
                    ErrorMessage.T(LatestFeedDetailActivity.this, "Action not found!");
                }
                break;
            case R.id.authore_tv:
                Bundle bundle = new Bundle();
                bundle.putString("Calling", "bypublisher");
                bundle.putString("Id", example.getUser().getId());
                bundle.putString("Name", example.getUser().getDisplayName());
                ErrorMessage.I(LatestFeedDetailActivity.this, DashBoardActivity.class, bundle);
                break;
        }
    }

    private void sendMessage() {
        if (fragmentTitle.equals("Listview")) {
            intent = new Intent("Listview");
        } else {
            intent = new Intent("Standerd");
        }
        intent.putExtra("message", example.getId());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void followOnServer() {
        if (NetworkUtil.isNetworkAvailable(LatestFeedDetailActivity.this)) {
            Call<ResponseBody> call = ApiClient.getLoadInterface().FollowNumerouns(Const.ENDPOINT.FollowCollections + "collection/" + example.getId() + "/" + SavedData.getTokan());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("followOnServer response" + response.code());
                    if (response.isSuccessful()) {
                        try {
                            ErrorMessage.E("error code" + response.body().string());
                            if (response.code() == 200) {
                                followBynImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_follow_green));
                            } else {
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        ErrorMessage.T(LatestFeedDetailActivity.this, "Response not successful");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(LatestFeedDetailActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());

                }
            });

        } else {
            ErrorMessage.T(LatestFeedDetailActivity.this, LatestFeedDetailActivity.this.getString(R.string.no_internet));
        }

    }

    private void getfollowOnServer() {
        try {
            if (NetworkUtil.isNetworkAvailable(LatestFeedDetailActivity.this)) {  ///apis/follow/:itemID/:userID?/:fcmtoken?'
                Call<ResponseBody> call = ApiClient.getLoadInterface().FollowNumerouns(Const.ENDPOINT.FollowCollections + example.getId() + "/" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getId() + "/" + SavedData.getTokan());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ErrorMessage.E("getfollowOnServererror code" + response.code());
                        if (response.isSuccessful()) {
                            try {
                                ErrorMessage.E("getfollowOnServer response" + response.body().string());
                                if (response.code() == 200) {
                                    followBynImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_follow_green));
                                } else {
                                }
                            } catch (Exception e) {

                            }
                        } else {
                           // ErrorMessage.T(LatestFeedDetailActivity.this, "Response not successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        ErrorMessage.T(LatestFeedDetailActivity.this, "Response Fail");
                        System.out.println("============update profile fail  :" + t.toString());

                    }
                });

            } else {
                ErrorMessage.T(LatestFeedDetailActivity.this, LatestFeedDetailActivity.this.getString(R.string.no_internet));
            }
        } catch (Exception e) {
        }

    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(LatestFeedDetailActivity.this, v);
        for (int i = 0; i < example.getActions().size(); i++) {
            popup.getMenu().add(Menu.NONE, i, i, example.getActions().get(i).getName());
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == 0) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + example.getActions().get(0).getUrl()));
                        startActivity(intent);
                    } catch (Exception e) {
                    }
                    return true;
                } else if (i == 1) {
                    try {
                        ErrorMessage.E("ACTION_DIAL" + example.getActions().get(1).getUrl());
                       /* Intent send = new Intent(Intent.ACTION_SENDTO);
                        send.setType("text/plain");
                        send.putExtra(Intent.EXTRA_EMAIL, example.getActions().get(1).getUrl());
                        send.putExtra(Intent.EXTRA_SUBJECT, "Numerical");
                        startActivity(Intent.createChooser(send, "Send message"));*/

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri data = Uri.parse("mailto:" + example.getActions().get(1).getUrl() + "?subject=" + example.getActions().get(1).getName());
                        intent.setData(data);
                        startActivity(intent);
                    } catch (Exception e) {
                        //e.toString();
                    }
                    return true;
                } else if (i == 2) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(example.getActions().get(2).getUrl()));
                        startActivity(browserIntent);
                    } catch (Exception e) {
                    }
                } else if (i == 3) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(example.getActions().get(3).getUrl()));
                        startActivity(browserIntent);
                    } catch (Exception e) {
                    }
                } else if (i == 4) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(example.getActions().get(4).getUrl()));
                        startActivity(browserIntent);
                    } catch (Exception e) {
                    }
                } else if (i == 5) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(example.getActions().get(5).getUrl()));
                        startActivity(browserIntent);
                    } catch (Exception e) {
                    }
                }

                return false;
            }
        });
        popup.show();
    }

    public void getCalllink(String url) {
        String[] bits = url.split("/");
        String lastOne = bits[bits.length - 1];
        GetLatestFeed(lastOne);
    }

    private void GetLatestFeed(String url) {
        if (NetworkUtil.isNetworkAvailable(LatestFeedDetailActivity.this)) {
            Call<ResponseBody> call = null;
            final Dialog materialDialog = ErrorMessage.initProgressDialog(LatestFeedDetailActivity.this);
            call = ApiClient.getLoadInterface().latest("https://numerical.co.in/apis/numerons/" + url);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            Gson gson = new Gson();
                            String responseData = jsonObject.toString();
                            example = gson.fromJson(responseData, Example.class);

                            try {
                                tilteTv.setText(example.getTitle());
                                authoreTv.setText(example.getUser().getDisplayName());
                                if (getIntent().getStringExtra("Name").equals("")) {
                                    if (example.getIsCollection()) {
                                        tilteTv.setVisibility(View.VISIBLE);
                                        setToolbarWithBackButton("Collection");
                                    } else {
                                        tilteTv.setVisibility(View.GONE);
                                        setToolbarWithBackButton("Numerons");
                                    }
                                } else {
                                    setToolbarWithBackButton(getIntent().getStringExtra("Name"));
                                    if (example.getIsCollection()) {
                                        tilteTv.setVisibility(View.VISIBLE);
                                    } else {
                                        tilteTv.setVisibility(View.GONE);
                                    }
                                }
                                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy - hh:mm a");
                                Date d = null;
                                try {
                                    d = input.parse(example.getLastModified());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                String formatted = output.format(d);
                                dateTv.setText(formatted);
                                ListingViewFragment presonalDetailsFragment1 = new ListingViewFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("ItemData", example);
                                presonalDetailsFragment1.setArguments(bundle);
                                mFragmentTransaction = detailsFragment1.beginTransaction();
                                mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
                                mFragmentTransaction.commit();

                            } catch (Exception e) {
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorMessage.E("JSONException" + e.toString());
                            ErrorMessage.T(LatestFeedDetailActivity.this, "Server Error");
                            materialDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                            ErrorMessage.E("IOException" + e.toString());
                            ErrorMessage.T(LatestFeedDetailActivity.this, "Server Error");
                            materialDialog.dismiss();
                        }


                    } else {
                        ErrorMessage.T(LatestFeedDetailActivity.this, "Response not successful");
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(LatestFeedDetailActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());
                    materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(LatestFeedDetailActivity.this, this.getString(R.string.no_internet));

        }

    }

    @OnClick(R.id.back_btn_tv)
    public void onClick() {
    }

    public void CallCommentScreen(String numeron_id) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        stringArrayList.add("");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LatestFeedDetailActivity.this);
        CommentAdapter side_rv_adapter = new CommentAdapter(LatestFeedDetailActivity.this, stringArrayList);
        commentRv.setLayoutManager(linearLayoutManager);
        commentRv.setItemAnimator(new DefaultItemAnimator());
        commentRv.setNestedScrollingEnabled(false);
        commentRv.setAdapter(side_rv_adapter);
        side_rv_adapter.notifyDataSetChanged();

        commentRelativelayout.setVisibility(View.VISIBLE);
        Animation animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        commentRelativelayout.startAnimation(animSlideUp);
        bottomCardview.setVisibility(View.GONE);
        GetCommentsOnServer(numeron_id);
    }

    private void GetCommentsOnServer(String numeron_id) {
        if (NetworkUtil.isNetworkAvailable(LatestFeedDetailActivity.this)) {
            ErrorMessage.E("GetCommentsOnServer request" + Const.ENDPOINT.Comment + example.getId() + "/numeron/" + numeron_id + "/comment");
            Call<ResponseBody> call = ApiClient.getLoadInterface().Comments(Const.ENDPOINT.Comment + example.getId() + "/numeron/"+numeron_id+"/comment");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("followOnServer response" + response.code());
                    if (response.isSuccessful()) {
                        try {
                            ErrorMessage.E("error code" + response.body().string());
                            if (response.code() == 200) {

                            } else {
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        //ErrorMessage.T(LatestFeedDetailActivity.this, "Response not successful");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(LatestFeedDetailActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());

                }
            });

        } else {
            ErrorMessage.T(LatestFeedDetailActivity.this, LatestFeedDetailActivity.this.getString(R.string.no_internet));
        }

    }

    public void getLikeButton() {
        likeImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_fill));
    }

  /*  private void SubmitCommentsOnServer(String numeron_id) {
        if (NetworkUtil.isNetworkAvailable(LatestFeedDetailActivity.this)) {
            ErrorMessage.E("GetCommentsOnServer request" + Const.ENDPOINT.Comment + example.getId() + "/numeron/" + numeron_id + "/comment");
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("comment", writeCommentEtv.getText().toString());
            Call<ResponseBody> call = ApiClient.getLoadInterface().SubmitComment(jsonObject);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("followOnServer response" + response.code());
                    if (response.isSuccessful()) {
                        try {
                            ErrorMessage.E("error code" + response.body().string());
                            if (response.code() == 200) {

                            } else {
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        ErrorMessage.T(LatestFeedDetailActivity.this, "Response not successful");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(LatestFeedDetailActivity.this, "Response Fail");
                    System.out.println("============update profile fail  :" + t.toString());

                }
            });

        } else {
            ErrorMessage.T(LatestFeedDetailActivity.this, LatestFeedDetailActivity.this.getString(R.string.no_internet));
        }

    }*/
}

