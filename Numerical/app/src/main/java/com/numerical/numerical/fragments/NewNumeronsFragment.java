package com.numerical.numerical.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numerical.numerical.R;
import com.numerical.numerical.fragments.EditCollections.StepFifthFragment;
import com.numerical.numerical.fragments.EditCollections.StepFirstFragment;
import com.numerical.numerical.fragments.EditCollections.StepFourthFragment;
import com.numerical.numerical.fragments.EditCollections.StepThirdFragment;
import com.numerical.numerical.fragments.EditCollections.StepTwoFragment;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;


public class NewNumeronsFragment extends Fragment {
    View view;
    public static StepView stepView;
    private FragmentTransaction mFragmentTransaction;

    /* FragmentManager detailsFragment1 = getActivity().getSupportFragmentManager();*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_numerons, container, false);
        stepView = (StepView) view.findViewById(R.id.step_view);
        stepView.setSteps(new ArrayList<String>() {{
            add("Step1");
            add("Step2");
            add("Step3");
            add("Step4");
            add("Step5");
        }});

        /*StepFirstFragment presonalDetailsFragment1 = new StepFirstFragment();
        mFragmentTransaction = detailsFragment1.beginTransaction();
        mFragmentTransaction.replace(R.id.stepsfragmentsLayout, presonalDetailsFragment1);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();*/


        StepFirstFragment newGamefragment = new StepFirstFragment();
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.stepsfragmentsLayout, newGamefragment);
        mFragmentTransaction.commit();
        return view;
    }

    public void CallStepTwo() {
        stepView.go(stepView.getCurrentStep() + 1, true);

    }
     public void CallStepThird() {
        stepView.go(stepView.getCurrentStep() + 1, true);

    } public void CallStepFourth() {
        stepView.go(stepView.getCurrentStep() + 1, true);

    }public void CallStepFifth() {
        stepView.go(stepView.getCurrentStep() + 1, true);

    }

}
