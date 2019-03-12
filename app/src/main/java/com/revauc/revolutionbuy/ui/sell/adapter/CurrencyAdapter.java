package com.revauc.revolutionbuy.ui.sell.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ItemCategoriesBinding;
import com.revauc.revolutionbuy.databinding.ItemCurrencyBinding;
import com.revauc.revolutionbuy.listeners.OnCategorySelectListener;
import com.revauc.revolutionbuy.listeners.OnCurrencySelectListener;
import com.revauc.revolutionbuy.network.response.buyer.CategoryDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;



public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {


    private final List<String> mCurrencyCodes;
    private List<String> arraylist;
    private final OnCurrencySelectListener onCurrencySelectListener;
    private Context mContext;
    private ItemCurrencyBinding mBinding;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrency;
        public MyViewHolder(View view) {
            super(view);
            tvCurrency = mBinding.textCurrency;

        }
    }

    public CurrencyAdapter(Context mContext, OnCurrencySelectListener onCurrencySelectListener) {

        this.mContext = mContext;
        this.onCurrencySelectListener = onCurrencySelectListener;
        mCurrencyCodes = new ArrayList<String>(Arrays.asList(mContext.getResources().getStringArray(R.array.currency_names)));
        arraylist = new ArrayList<>();
        arraylist.addAll(mCurrencyCodes);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_currency, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvCurrency.setText(mCurrencyCodes.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCurrencySelectListener.onCurrencySelected(mCurrencyCodes.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCurrencyCodes.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mCurrencyCodes.clear();
        if (charText.length() == 0) {
            mCurrencyCodes.addAll(arraylist);
        } else {
            for (String wp : arraylist) {
                if (wp.toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    mCurrencyCodes.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
