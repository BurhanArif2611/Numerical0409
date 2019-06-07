package com.numerical.numerical.fragments.EditCollections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numerical.numerical.R;
import com.numerical.numerical.activity.PregerenceActivity;
import com.numerical.numerical.adapters.AddMoreCustomeAdapter;
import com.numerical.numerical.adapters.feed_Adapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class StepFifthFragment extends Fragment {
    View view;
    @BindView(R.id.add_more_customeaction_tv)
    TextView addMoreCustomeactionTv;
    Unbinder unbinder;
    @BindView(R.id.add_more_list_rv)
    RecyclerView addMoreListRv;
    ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_step_fifth, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_more_customeaction_tv)
    public void onClick() {
        stringArrayList.add("");
        addMoreListRv.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        AddMoreCustomeAdapter side_rv_adapter = new AddMoreCustomeAdapter(getActivity(), stringArrayList);
        addMoreListRv.setLayoutManager(linearLayoutManager);
        addMoreListRv.setItemAnimator(new DefaultItemAnimator());
        addMoreListRv.setNestedScrollingEnabled(false);
        addMoreListRv.setAdapter(side_rv_adapter);
        side_rv_adapter.notifyDataSetChanged();
    }
}
