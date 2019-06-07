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


public class StepFourthFragment extends Fragment {


    @BindView(R.id.next_btn)
    TextView nextBtn;
    Unbinder unbinder;
    @BindView(R.id.select_type_spinner)
    Spinner selectTypeSpinner;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_fourth, container, false);
        unbinder = ButterKnife.bind(this, view);
        List<String> list = new ArrayList<String>();
        list.add("Select collection");
        list.add("List");
        list.add("Standard");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTypeSpinner.setAdapter(dataAdapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.next_btn)
    public void onClick() {
        new NewNumeronsFragment().CallStepFifth();
        StepFifthFragment newGamefragment = new StepFifthFragment();
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.stepsfragmentsLayout, newGamefragment);
        mFragmentTransaction.commit();
    }
}
