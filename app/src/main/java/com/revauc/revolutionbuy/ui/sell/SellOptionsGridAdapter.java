package com.revauc.revolutionbuy.ui.sell;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;



public class SellOptionsGridAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater mInflater;
    String[] titles;
    TypedArray imgsArray;

    public SellOptionsGridAdapter(Context context,String[] titles, TypedArray imgsArray) {
        this.context = context;
        this.titles = titles;
        this.imgsArray = imgsArray;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MyHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_dashboard_grid,
                    null);
            holder = new MyHolder();
            holder.tvTitle = (TextView)convertView.findViewById(R.id.text_title);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }

        //Setting label
        holder.tvTitle.setText(titles[i]);
        holder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(0,imgsArray.getResourceId(i, -1),0,0);

        return convertView;
    }

    public class MyHolder {
        public TextView tvTitle;

    }

}
