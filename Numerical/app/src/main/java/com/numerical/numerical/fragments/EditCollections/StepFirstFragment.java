package com.numerical.numerical.fragments.EditCollections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.numerical.numerical.R;
import com.numerical.numerical.fragments.NewNumeronsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class StepFirstFragment extends Fragment {
    View view;
    @BindView(R.id.next_btn)
    TextView nextBtn;
    Unbinder unbinder;

    private FragmentTransaction mFragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_step_first, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.next_btn)
    public void onClick() {
        new NewNumeronsFragment().CallStepTwo();
        StepTwoFragment newGamefragment = new StepTwoFragment();
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.stepsfragmentsLayout, newGamefragment);
        mFragmentTransaction.commit();
    }
}
