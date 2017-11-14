package com.revauc.revolutionbuy.ui.auth.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ItemCurrencyBinding;
import com.revauc.revolutionbuy.listeners.OnCountrySelectListener;
import com.revauc.revolutionbuy.listeners.OnStateSelectListener;
import com.revauc.revolutionbuy.network.response.profile.CountryDto;
import com.revauc.revolutionbuy.network.response.profile.StateDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {


    private final List<StateDto> countries;
    private List<StateDto> arraylist = new ArrayList<>();
    private final OnStateSelectListener onCountrySelectListener;
    private Context mContext;
    private ItemCurrencyBinding mBinding;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrency;
        public MyViewHolder(View view) {
            super(view);
            tvCurrency = mBinding.textCurrency;

        }
    }

    public StateAdapter(Context mContext, List<StateDto> countries, OnStateSelectListener onCountrySelectListener) {

        this.mContext = mContext;
        this.onCountrySelectListener = onCountrySelectListener;
        this.countries = countries;
        arraylist = new ArrayList<>();
        arraylist.addAll(countries);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_currency, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvCurrency.setText(countries.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCountrySelectListener.onStateSelected(countries.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        countries.clear();
        if (charText.length() == 0) {
            countries.addAll(arraylist);
        } else {
            for (StateDto wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    countries.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
