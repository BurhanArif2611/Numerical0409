package com.androidboy.directory.futureInvestment.adafter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidboy.directory.futureInvestment.R;
import com.androidboy.directory.futureInvestment.fragment.FreeTrailSecondFragment;

import java.util.ArrayList;

public class SegmentAdapter extends RecyclerView.Adapter<SegmentAdapter.RecyclerViewHolders> {

    ArrayList<String> callList;
    Context context;



    public SegmentAdapter(ArrayList<String> callList, Context context) {
        this.callList = callList;
        this.context = context;
    }

    /*public CallRVAdapter(ArrayList<CallGetList> lists, FragmentActivity activity)
    {

    }*/

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.segment_layout, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        final int[] num = {1};
        holder.stock_cash_etv.setText(callList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num[0] % 2 == 0) {
                    num[0]++;
                    System.out.println("Entered number is even");
                    holder.stock_cash_etv.setTextColor(context.getResources().getColor(R.color.gray));
                    holder.stock_cash_etv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick_grey, 0);
                    new FreeTrailSecondFragment().SetSegment(callList.get(position), position);
                } else {
                    num[0]++;
                    System.out.println("Entered number is odd");
                    holder.stock_cash_etv.setTextColor(context.getResources().getColor(R.color.black));
                    holder.stock_cash_etv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick_orange, 0);
                    new FreeTrailSecondFragment().SetSegment(callList.get(position), position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.callList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder {
        private TextView stock_cash_etv;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            stock_cash_etv = (TextView) itemView.findViewById(R.id.stock_cash_etv);
        }
    }
}
