package com.numerical.numerical.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.numerical.numerical.Models.LatestFeed.Example;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ApiClient;
import com.numerical.numerical.Utility.Const;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.Utility.NetworkUtil;
import com.numerical.numerical.activity.LatestFeedDetailActivity;
import com.numerical.numerical.adapters.Latest_numeruns_Adapter;
import com.numerical.numerical.adapters.Tags_Adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ListingViewFragment extends Fragment {
    View view;
    @BindView(R.id.item_img)
    ImageView itemImg;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.likecount_tv)
    TextView likecountTv;
    @BindView(R.id.view_tv)
    TextView viewTv;
    @BindView(R.id.comment_tv)
    TextView commentTv;
    @BindView(R.id.list_describtion_rv)
    RecyclerView listDescribtionRv;
    @BindView(R.id.tags_rv)
    RecyclerView tagsRv;
    @BindView(R.id.source_tv)
    TextView sourceTv;
    TextView placeofimage_tv, describtion_tv;
    Unbinder unbinder;

    private Example example;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_listing_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        itemImg = (ImageView) view.findViewById(R.id.item_img);
        dateTv = (TextView) view.findViewById(R.id.date_tv);
        likecountTv = (TextView) view.findViewById(R.id.likecount_tv);
        viewTv = (TextView) view.findViewById(R.id.view_tv);
        commentTv = (TextView) view.findViewById(R.id.comment_tv);
        sourceTv = (TextView) view.findViewById(R.id.source_tv);
        placeofimage_tv = (TextView) view.findViewById(R.id.placeofimage_tv);
        describtion_tv = (TextView) view.findViewById(R.id.describtion_tv);
        listDescribtionRv = (RecyclerView) view.findViewById(R.id.list_describtion_rv);
        tagsRv = (RecyclerView) view.findViewById(R.id.tags_rv);

        ((LatestFeedDetailActivity) getActivity()).launchFragmentTitle("Listview");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            try {
                example = (Example) getArguments().getSerializable("ItemData");
                viewTv.setText("" + example.getViews());
                sourceTv.setText(example.getSource());
                if (example.getLikes() == null) {
                    likecountTv.setText("0");
                } else {
                    likecountTv.setText("" + example.getLikes());
                }
                if (example.getIsCollection()) {
                    placeofimage_tv.setVisibility(View.GONE);
                    describtion_tv.setVisibility(View.GONE);
                    Glide.with(getActivity()).load(example.getAssetPath()).into(itemImg);
                } else {
                    sourceTv.setText(example.getNumerons().get(0).getSource());
                    itemImg.setVisibility(View.GONE);
                    placeofimage_tv.setVisibility(View.VISIBLE);
                    describtion_tv.setVisibility(View.VISIBLE);
                    placeofimage_tv.setText(example.getNumerons().get(0).getNumeral());
                    SpannableStringBuilder builder = new SpannableStringBuilder();

                    SpannableString str1 = new SpannableString(example.getNumerons().get(0).getNumeral());
                    str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_text)), 0, str1.length(), 0);
                    str1.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_size_20)), 0, str1.length(), SPAN_INCLUSIVE_INCLUSIVE); // set size
                    str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.append(str1);

                    SpannableString str2 = new SpannableString(" " + example.getNumerons().get(0).getTitle());
                    str2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.secondary_text)), 0, str2.length(), 0);
                    builder.append(str2);
                    describtion_tv.setText(builder, TextView.BufferType.SPANNABLE);
                    if (example.getNumerons().get(0).getSubType().equals("panel-angry")) {
                        placeofimage_tv.setBackgroundColor(getResources().getColor(R.color.panel_angry));
                    } else if (example.getNumerons().get(0).getSubType().equals("panel-formal")) {
                        placeofimage_tv.setBackgroundColor(getResources().getColor(R.color.panel_formal));
                    } else if (example.getNumerons().get(0).getSubType().equals("panel-sad")) {
                        placeofimage_tv.setBackgroundColor(getResources().getColor(R.color.panel_sad));
                    } else if (example.getNumerons().get(0).getSubType().equals("panel-calm")) {
                        placeofimage_tv.setBackgroundColor(getResources().getColor(R.color.panel_calm));
                    } else if (example.getNumerons().get(0).getSubType().equals("panel-positive")) {
                        placeofimage_tv.setBackgroundColor(getResources().getColor(R.color.panel_positive));
                    } else if (example.getNumerons().get(0).getSubType().equals("panel-royal")) {
                        placeofimage_tv.setBackgroundColor(getResources().getColor(R.color.panel_royal));
                    } else if (example.getNumerons().get(0).getSubType().equals("panel-happy")) {
                        placeofimage_tv.setBackgroundColor(getResources().getColor(R.color.panel_happy));
                    } else if (example.getNumerons().get(0).getSubType().equals("Standard-IMAGE")) {
                        itemImg.setVisibility(View.VISIBLE);
                        placeofimage_tv.setVisibility(View.GONE);
                        describtion_tv.setVisibility(View.VISIBLE);
                        placeofimage_tv.setText(example.getNumerons().get(0).getNumeral());
                        describtion_tv.setText(builder, TextView.BufferType.SPANNABLE);
                        if (example.getNumerons().get(0).getAssetPath().contains("https://")) {
                            Glide.with(getActivity()).load(example.getNumerons().get(0).getAssetPath()).into(itemImg);
                        } else if (example.getNumerons().get(0).getAssetPath().contains("http://")) {
                            Glide.with(getActivity()).load(example.getNumerons().get(0).getAssetPath()).into(itemImg);
                        } else {
                            Glide.with(getActivity()).load("https://numerical.co.in" + example.getNumerons().get(0).getAssetPath()).into(itemImg);
                        }

                    } else if (example.getNumerons().get(0).getSubType().equals("IMAGE")) {
                        itemImg.setVisibility(View.VISIBLE);
                        placeofimage_tv.setVisibility(View.GONE);
                        describtion_tv.setVisibility(View.VISIBLE);
                        placeofimage_tv.setText(example.getNumerons().get(0).getNumeral());
                        describtion_tv.setText(builder, TextView.BufferType.SPANNABLE);
                        if (example.getNumerons().get(0).getAssetPath().contains("https://")) {
                            Glide.with(getActivity()).load(example.getNumerons().get(0).getAssetPath()).into(itemImg);
                        } else if (example.getNumerons().get(0).getAssetPath().contains("http://")) {
                            Glide.with(getActivity()).load(example.getNumerons().get(0).getAssetPath()).into(itemImg);
                        } else {
                            Glide.with(getActivity()).load("https://numerical.co.in" + example.getNumerons().get(0).getAssetPath()).into(itemImg);
                        }

                    }
                    listDescribtionRv.setVisibility(View.GONE);
                }

                Latest_numeruns_Adapter side_rv_adapter = new Latest_numeruns_Adapter(getActivity(), example.getNumerons());
                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
                listDescribtionRv.setLayoutManager(gridLayoutManager);
                listDescribtionRv.setItemAnimator(new DefaultItemAnimator());
                listDescribtionRv.setNestedScrollingEnabled(false);
                listDescribtionRv.setAdapter(side_rv_adapter);
                listDescribtionRv.setHasFixedSize(false);
                listDescribtionRv.smoothScrollToPosition(1);

                Tags_Adapter tags_adapter = new Tags_Adapter(getActivity(), example.getCategories());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                tagsRv.setLayoutManager(linearLayoutManager);
                tagsRv.setItemAnimator(new DefaultItemAnimator());
                tagsRv.setAdapter(tags_adapter);
                tags_adapter.notifyDataSetChanged();
                tagsRv.smoothScrollToPosition(1);

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

            } catch (Exception e) {
            }

        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("Listview"));

        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //((LatestFeedDetailActivity)getActivity()).CallCommentScreen(example.getNumerons().get(0).getId());
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:" + "" + "?subject=" + example.getTitle());
                    intent.setData(data);
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        });
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.source_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.source_tv:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(example.getUrl()));
                startActivity(browserIntent);
                break;
        }
    }

    private void LikeOnServer() {
        try {
            if (NetworkUtil.isNetworkAvailable(getActivity())) {
                Call<ResponseBody> call = ApiClient.getLoadInterface().LikeNumerouns(Const.ENDPOINT.LikeNumerouns + example.getId() + "/like");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ErrorMessage.E("error code" + response.code());
                        if (response.isSuccessful()) {
                            try {
                                int Count = 0;
                                if (response.code() == 200) {

                                    Count = Integer.parseInt(likecountTv.getText().toString());
                                    Count++;
                                    ErrorMessage.E("Count" + Count);
                                    likecountTv.setText("" + Count);
                                    ((LatestFeedDetailActivity) getActivity()).getLikeButton();

                                } else {
                                }
                            } catch (Exception e) {

                            }
                        } else {
                            ErrorMessage.T(getActivity(), "Response not successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("============update profile fail  :" + t.toString());

                    }
                });

            } else {
                ErrorMessage.T(getActivity(), getActivity().getString(R.string.no_internet));
            }
        } catch (Exception e) {
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            LikeOnServer();
            Log.e("receiverOnFragment", "Got message: " + message);
        }
    };
}
