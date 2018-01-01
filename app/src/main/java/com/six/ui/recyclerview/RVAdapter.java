package com.six.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.six.ui.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVHolder> {
    private int[] heights;
    private List<String> mData = new ArrayList<>();
    private Context context;

    public RVAdapter(Context context, List<String> data) {
        this.context = context;
        Random rd = new Random();
        heights = new int[data.size()];
        mData = data;
        for (int i = 0; i < mData.size(); i++) {
            heights[i] = rd.nextInt(300) + 100;
        }
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RVHolder(LayoutInflater.from(context).inflate(R.layout.rv_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RVHolder rvHolder, int i) {
        rvHolder.tvItem.setText(mData.get(i));
        //to set waterfall effect
//            rvHolder.tvItem.setHeight(heights[i]);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void addData(int position) {
        mData.add(position, "item " + position);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    class RVHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public RVHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}