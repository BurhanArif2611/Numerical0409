package com.numerical.numerical.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.activity.ChangePasswordActivity;
import com.numerical.numerical.activity.DashBoardActivity;
import com.numerical.numerical.activity.MyProfileActivity;
import com.numerical.numerical.activity.PregerenceActivity;
import com.numerical.numerical.activity.SplashActivity;
import com.numerical.numerical.database.UserProfileHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ProfileFragment extends Fragment {
    View view;
    @BindView(R.id.myprofile_cardview)
    CardView myprofileCardview;
    @BindView(R.id.preference_cardview)
    CardView preferenceCardview;
    @BindView(R.id.changepassword_cardview)
    CardView changepasswordCardview;
    @BindView(R.id.signout_cardview)
    CardView signoutCardview;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((DashBoardActivity) getActivity()).launchFragmentTitle("Profile");
        //((DashBoardActivity) getActivity()).GetCurrentPosition("");
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.myprofile_cardview, R.id.preference_cardview, R.id.changepassword_cardview, R.id.signout_cardview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myprofile_cardview:
                ErrorMessage.I(getActivity(), MyProfileActivity.class,null);
                break;
            case R.id.preference_cardview:
                ErrorMessage.I(getActivity(), PregerenceActivity.class,null);
                break;
            case R.id.changepassword_cardview:
                ErrorMessage.I(getActivity(), ChangePasswordActivity.class,null);
                break;
            case R.id.signout_cardview:
                LogoutPopUP();
                break;
        }
    }
    public void LogoutPopUP() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.logout_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button yes_btn = (Button) dialog.findViewById(R.id.yes_btn);
        final Button no_btn = (Button) dialog.findViewById(R.id.no_btn);
        yes_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                UserProfileHelper.getInstance().delete();
                ErrorMessage.I_clear(getActivity(), SplashActivity.class, null);

            }
        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
