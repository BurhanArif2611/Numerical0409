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
import com.revauc.revolutionbuy.listeners.OnCurrencySelectListener;
import com.revauc.revolutionbuy.network.response.profile.CountryDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {


    private final List<CountryDto> countries;
    private List<CountryDto> arraylist = new ArrayList<>();
    private final OnCountrySelectListener onCountrySelectListener;
    private Context mContext;
    private ItemCurrencyBinding mBinding;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrency;
        public MyViewHolder(View view) {
            super(view);
            tvCurrency = mBinding.textCurrency;

        }
    }

    public CountryAdapter(Context mContext,List<CountryDto> countries, OnCountrySelectListener onCountrySelectListener) {

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
                onCountrySelectListener.onCountrySelected(countries.get(position));
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
            for (CountryDto wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    countries.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
