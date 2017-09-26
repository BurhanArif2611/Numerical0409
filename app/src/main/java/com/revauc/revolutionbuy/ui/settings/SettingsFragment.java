package com.revauc.revolutionbuy.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.FragmentSettingsBinding;
import com.revauc.revolutionbuy.eventbusmodel.OnButtonClicked;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.PrivacyActivity;
import com.revauc.revolutionbuy.ui.auth.TermsConditionsActivity;
import com.revauc.revolutionbuy.ui.auth.adapters.ContactAdminActivity;
import com.revauc.revolutionbuy.ui.profile.ProfileActivity;
import com.revauc.revolutionbuy.ui.walkthrough.WalkThroughActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.PreferenceUtil;
import com.revauc.revolutionbuy.widget.BottomSheetAlert;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private FragmentSettingsBinding mBinder;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);

        return mBinder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        mBinder.toolbarSettings.txvToolbarGeneralCenter.setText(R.string.settings);
        mBinder.textYourProfile.setOnClickListener(this);
        mBinder.textOnboarding.setOnClickListener(this);
        mBinder.textTermsOfUse.setOnClickListener(this);
        mBinder.textPrivacy.setOnClickListener(this);
        mBinder.textChangePassword.setOnClickListener(this);
        mBinder.textContactAdmin.setOnClickListener(this);
        mBinder.textLogout.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.text_your_profile:
                if(PreferenceUtil.isLoggedIn())
                {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra(Constants.EXTRA_FROM_SETTINGS,true);
                    startActivity(intent);
                }
                else
                {

                }
                    break;
            case R.id.text_logout:
                BottomSheetAlert.getInstance(getActivity(),getString(R.string.sure_to_logout),getString(R.string.logout),getString(R.string.cancel)).show();
                break;
            case R.id.text_onboarding:
                Intent walkthroughIntent = new Intent(getActivity(), WalkThroughActivity.class);
                walkthroughIntent.putExtra(Constants.EXTRA_FROM_SETTINGS,true);
                startActivity(walkthroughIntent);
                break;
            case R.id.text_terms_of_use:
                Intent termsIntent = new Intent(getActivity(), TermsConditionsActivity.class);
                termsIntent.putExtra(Constants.EXTRA_FROM_SETTINGS,true);
                startActivity(termsIntent);
                break;
            case R.id.text_privacy:
                Intent privacyIntent = new Intent(getActivity(), PrivacyActivity.class);
                privacyIntent.putExtra(Constants.EXTRA_FROM_SETTINGS,true);
                startActivity(privacyIntent);
                break;
            case R.id.text_contact_admin:
                Intent adminIntent = new Intent(getActivity(), ContactAdminActivity.class);
                adminIntent.putExtra(Constants.EXTRA_FROM_SETTINGS,true);
                startActivity(adminIntent);
                break;
            case R.id.text_change_password:
                Intent passwordIntent = new Intent(getActivity(), ChangePasswordActivity.class);
                passwordIntent.putExtra(Constants.EXTRA_FROM_SETTINGS,true);
                startActivity(passwordIntent);
                break;
        }
    }

    @Subscribe
    public void onLogout(OnButtonClicked onLogoutClicked)
    {
        if(PreferenceUtil.isLoggedIn())
        {
            ((BaseActivity)getActivity()).logoutUserApi();
        }
        else
        {
            ((BaseActivity)getActivity()).logoutUser();
        }

    }
}
