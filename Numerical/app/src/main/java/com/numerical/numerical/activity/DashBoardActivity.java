package com.numerical.numerical.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.database.UserProfileHelper;
import com.numerical.numerical.fragments.DashboardFragment;
import com.numerical.numerical.fragments.MostViewedFragment;
import com.numerical.numerical.fragments.NewNumeronsFragment;
import com.numerical.numerical.fragments.ProfileFragment;
import com.numerical.numerical.fragments.SearchFragment;
import com.numerical.numerical.fragments.TopisFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoardActivity extends AppCompatActivity {

    @BindView(R.id.without_signin_home_tv)
    TextView withoutSigninHomeTv;
    @BindView(R.id.without_signin_helpsupport_tv)
    TextView withoutSigninHelpsupportTv;
    @BindView(R.id.signin_tv)
    TextView signinTv;
    @BindView(R.id.signup_tv)
    TextView signupTv;
    @BindView(R.id.without_signin_menus_layout)
    LinearLayout withoutSigninMenusLayout;
    @BindView(R.id.home_tv)
    TextView homeTv;
    @BindView(R.id.mynumerical_tv)
    TextView mynumericalTv;
    @BindView(R.id.helpsupport_tv)
    TextView helpsupportTv;
    @BindView(R.id.signout_tv)
    TextView signoutTv;
    @BindView(R.id.with_signin_menus_layout)
    LinearLayout withSigninMenusLayout;
    @BindView(R.id.add_fab)
    FloatingActionButton addFab;
    @BindView(R.id.bottomhome_tv)
    ImageView bottom_homeTv;
    @BindView(R.id.bottom_cardview)
    CardView bottomCardview;
    @BindView(R.id.bottom_search_tv)
    ImageView bottomSearchTv;
    @BindView(R.id.searchView1)
    MaterialSearchBar searchView1;

    private View menuItemView;
    private FragmentTransaction mFragmentTransaction;
    FragmentManager detailsFragment1 = getSupportFragmentManager();
    private View action_settings, latest, most_viewed, topic_art;
    private String fragmentTitle = "";
    DrawerLayout drawer;
    private String Calling = "";
    private String Id = "";
    private String Name = "";
    String LastCall = "";
    private FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }

        @SuppressLint("ResourceType") ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.drawable.ic_menu, R.drawable.ic_menu);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);

        CircleImageView user_imageView = (CircleImageView) header.findViewById(R.id.user_imageView);
        TextView user_name_tv = (TextView) header.findViewById(R.id.user_name_tv);
        TextView user_conatct_tv = (TextView) header.findViewById(R.id.user_conatct_tv);
        ImageView next_img = (ImageView) header.findViewById(R.id.next_img);
        LinearLayout without_signin_layout = (LinearLayout) header.findViewById(R.id.without_signin_layout);
        LinearLayout signin_linlayout = (LinearLayout) header.findViewById(R.id.signin_linlayout);


        signoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutPopUP();
            }
        });

        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            signin_linlayout.setVisibility(View.VISIBLE);
            withSigninMenusLayout.setVisibility(View.VISIBLE);
            without_signin_layout.setVisibility(View.GONE);
            withoutSigninMenusLayout.setVisibility(View.GONE);
            addFab.setVisibility(View.VISIBLE);
            user_name_tv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
            user_conatct_tv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getEmaiiId());
        } else {
            without_signin_layout.setVisibility(View.VISIBLE);
            withoutSigninMenusLayout.setVisibility(View.VISIBLE);
            signin_linlayout.setVisibility(View.GONE);
            withSigninMenusLayout.setVisibility(View.GONE);
            addFab.setVisibility(View.GONE);
        }
        signin_linlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                ProfileFragment presonalDetailsFragment1 = new ProfileFragment();
                mFragmentTransaction = detailsFragment1.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        });

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Calling = bundle.getString("Calling");
            Id = bundle.getString("Id");
            Name = bundle.getString("Name");
            if (Calling.equals("bypublisher")) {
                ByPublisher(Calling, Id, Name);
            } else if (Calling.equals("Tags")) {
                ByTags(Calling, Id, Name);
            }
        } else {
            FirebaseAnalytics("", "Lates");
            DashboardFragment presonalDetailsFragment1 = new DashboardFragment();
            mFragmentTransaction = detailsFragment1.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
            mFragmentTransaction.commitAllowingStateLoss();
        }
        bottomCardview.setVisibility(View.VISIBLE);

       /* int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) searchView1.findViewById(searchSrcTextId);
        searchEditText.setTextColor(getResources().getColor(R.color.primary_text)); // set the text color
        searchEditText.setHintTextColor(getResources().getColor(R.color.secondary_text));
        searchEditText.setFocusable(true);*/
        searchView1.setSpeechMode(true);

        searchView1.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                ErrorMessage.E("onSearchStateChanged " );
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                ErrorMessage.E("onButtonClicked " +searchView1.getText().toString());
                Intent intent = new Intent("Search");
                intent.putExtra("message", searchView1.getText().toString());
                LocalBroadcastManager.getInstance(DashBoardActivity.this).sendBroadcast(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }


    /*@Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        menuItemView = findViewById(R.id.form_setting);
        action_settings = findViewById(R.id.action_notification);
        latest = findViewById(R.id.latest);
        most_viewed = findViewById(R.id.most_viewed);
        topic_art = findViewById(R.id.topic_art);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        action_settings = findViewById(R.id.action_notification);
        latest = findViewById(R.id.latest);
        most_viewed = findViewById(R.id.most_viewed);
        topic_art = findViewById(R.id.topic_art);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            ErrorMessage.I(DashBoardActivity.this,NotificationActivity.class,null);
            return true;
        } else if (id == R.id.form_setting) {
           /* menuItemView = findViewById(R.id.form_setting);
            showPopup(menuItemView);*/
            return true;
        } else if (id == R.id.most_viewed) {
            FirebaseAnalytics("", "MostViewed");
            MostViewedFragment presonalDetailsFragment1 = new MostViewedFragment();
            mFragmentTransaction = detailsFragment1.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        } else if (id == R.id.topic_art) {
            FirebaseAnalytics("", "Topics");
            TopisFragment presonalDetailsFragment1 = new TopisFragment();
            mFragmentTransaction = detailsFragment1.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        } else if (id == R.id.latest) {
            FirebaseAnalytics("", "Lates");
            DashboardFragment presonalDetailsFragment1 = new DashboardFragment();
            mFragmentTransaction = detailsFragment1.beginTransaction();
            mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }


    public void launchFragmentTitle(String fragemntName) {
        fragmentTitle = fragemntName;
        if (fragemntName.equals("MostNumerons")) {
            getSupportActionBar().setTitle("Numerons");
        } else {
            getSupportActionBar().setTitle(fragemntName);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        if (fragmentTitle.equals("MostNumerons")) {
            menu.findItem(R.id.action_notification).setVisible(true);
            menu.findItem(R.id.form_setting).setVisible(true).setIcon(getResources().getDrawable(R.drawable.ic_most_viewed));
            menu.findItem(R.id.latest).setVisible(true);
            menu.findItem(R.id.most_viewed).setVisible(false);
            menu.findItem(R.id.topic_art).setVisible(true);
        } else if (fragmentTitle.equals("Topics")) {
            menu.findItem(R.id.form_setting).setVisible(true).setIcon(getResources().getDrawable(R.drawable.ic_topics));
            menu.findItem(R.id.action_notification).setVisible(true);
            menu.findItem(R.id.latest).setVisible(true);
            menu.findItem(R.id.most_viewed).setVisible(true);
            menu.findItem(R.id.topic_art).setVisible(false);
        } else if (fragmentTitle.equals("Numerons")) {
            menu.findItem(R.id.action_notification).setVisible(true);
            menu.findItem(R.id.form_setting).setVisible(true);
            menu.findItem(R.id.latest).setVisible(false);
            menu.findItem(R.id.most_viewed).setVisible(true);
            menu.findItem(R.id.topic_art).setVisible(true);

        } else if (fragmentTitle.equals("Profile")) {
            menu.findItem(R.id.action_notification).setVisible(true);
            menu.findItem(R.id.form_setting).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void LogoutPopUP() {
        final Dialog dialog = new Dialog(DashBoardActivity.this);
        dialog.setContentView(R.layout.logout_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button yes_btn = (Button) dialog.findViewById(R.id.yes_btn);
        final Button no_btn = (Button) dialog.findViewById(R.id.no_btn);
        yes_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                UserProfileHelper.getInstance().delete();
                ErrorMessage.I_clear(DashBoardActivity.this, SplashActivity.class, null);

            }
        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint("RestrictedApi")
    @OnClick({R.id.without_signin_home_tv, R.id.without_signin_helpsupport_tv, R.id.signin_tv, R.id.signup_tv, R.id.without_signin_menus_layout, R.id.mynumerical_tv, R.id.helpsupport_tv, R.id.signout_tv, R.id.with_signin_menus_layout, R.id.bottomhome_tv, R.id.home_tv, R.id.add_fab, R.id.bottom_search_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.without_signin_home_tv:
                drawer.closeDrawer(GravityCompat.START);
                DashboardFragment presonalDetailsFragment1 = new DashboardFragment();
                mFragmentTransaction = detailsFragment1.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
                //mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            case R.id.without_signin_helpsupport_tv:
                break;
            case R.id.signin_tv:
                ErrorMessage.I_clear(DashBoardActivity.this, LoginActivity.class, null);
                break;
            case R.id.signup_tv:
                ErrorMessage.I_clear(DashBoardActivity.this, SignUpActivity.class, null);
                break;
            case R.id.without_signin_menus_layout:
                break;
            case R.id.mynumerical_tv:
                break;
            case R.id.helpsupport_tv:
                break;
            case R.id.add_fab:
                bottomCardview.setVisibility(View.GONE);
                addFab.setVisibility(View.GONE);
                NewNumeronsFragment newNumeronsFragment = new NewNumeronsFragment();
                mFragmentTransaction = detailsFragment1.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentsLayout, newNumeronsFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            case R.id.signout_tv:
                LogoutPopUP();
                break;
            case R.id.with_signin_menus_layout:
                break;
            case R.id.bottomhome_tv:
                searchView1.setVisibility(View.GONE);
                DashboardFragment dashboardFragment1 = new DashboardFragment();
                mFragmentTransaction = detailsFragment1.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentsLayout, dashboardFragment1);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            case R.id.home_tv:
                drawer.closeDrawer(GravityCompat.START);
                DashboardFragment dashboardFragment = new DashboardFragment();
                mFragmentTransaction = detailsFragment1.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentsLayout, dashboardFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            case R.id.bottom_search_tv:
                searchView1.setVisibility(View.VISIBLE);
                SearchFragment searchFragment = new SearchFragment();
                mFragmentTransaction = detailsFragment1.beginTransaction();
                mFragmentTransaction.replace(R.id.fragmentsLayout, searchFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;

        }
    }

    public void Topics(String Calling, String Topics_Id, String Name) {
        FirebaseAnalytics(Topics_Id, Name);
        DashboardFragment presonalDetailsFragment1 = new DashboardFragment();
        FragmentManager detailsFragment1 = getSupportFragmentManager();
        mFragmentTransaction = detailsFragment1.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
        mFragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putString("Calling", Calling);
        bundle.putString("Id", Topics_Id);
        bundle.putString("Name", Name);
        presonalDetailsFragment1.setArguments(bundle);
        mFragmentTransaction.commit();
    }

    public void Bycategory(String Calling, String Topics_Id) {
        DashboardFragment presonalDetailsFragment1 = new DashboardFragment();
        FragmentManager detailsFragment1 = getSupportFragmentManager();
        mFragmentTransaction = detailsFragment1.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
        mFragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putString("Calling", Calling);
        bundle.putString("Id", Topics_Id);
        bundle.putString("Name", Topics_Id);
        presonalDetailsFragment1.setArguments(bundle);
        mFragmentTransaction.commit();
    }

    public void ByPublisher(String Calling, String Topics_Id, String Name) {
        FirebaseAnalytics(Topics_Id, Name);
        DashboardFragment presonalDetailsFragment1 = new DashboardFragment();
        FragmentManager detailsFragment1 = getSupportFragmentManager();
        mFragmentTransaction = detailsFragment1.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
        mFragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putString("Calling", Calling);
        bundle.putString("Id", Topics_Id);
        bundle.putString("Name", Name);
        presonalDetailsFragment1.setArguments(bundle);
        mFragmentTransaction.commit();
    }

    public void ByTags(String Calling, String Topics_Id, String Name) {
        FirebaseAnalytics(Topics_Id, Name);
        DashboardFragment presonalDetailsFragment1 = new DashboardFragment();
        FragmentManager detailsFragment1 = getSupportFragmentManager();
        mFragmentTransaction = detailsFragment1.beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentsLayout, presonalDetailsFragment1);
        mFragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putString("Calling", Calling);
        bundle.putString("Id", Topics_Id);
        bundle.putString("Name", Name);
        presonalDetailsFragment1.setArguments(bundle);
        mFragmentTransaction.commit();
    }

    /*@Override
    public void finish() {
        if (!LastCall.equals("")) {
            FinishPopUP();
        }
    }
*/
    public void FinishPopUP() {
        final Dialog dialog = new Dialog(DashBoardActivity.this);
        dialog.setContentView(R.layout.confirmation_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextView content_tv = (TextView) dialog.findViewById(R.id.content_tv);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);
        final Button continue_video = (Button) dialog.findViewById(R.id.continue_video);
        content_tv.setText("Are you Sure ! you want to exit app .");
        continue_video.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                moveTaskToBack(true);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /*public void GetCurrentPosition(String Status) {
        LastCall = Status;
    }*/
    public void FirebaseAnalytics(String id, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        /* bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");*/
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    @Override
    public void onBackPressed() {
        searchView1.setVisibility(View.GONE);
        super.onBackPressed();
    }
}
