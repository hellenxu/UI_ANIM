package com.six.sixua.simulation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.six.sixua.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-11-01.
 */

public class TransparentAdapter extends RecyclerView.Adapter {
    private List<String> data = new ArrayList<>();
    private LayoutInflater inflater;

    private static final int ITEM_CONTENT = 1;
    private static final int ITEM_AD = 0;
    private static final int TYPE_INTERVAL = 7;

    public TransparentAdapter(Context ctx, List<String> data) {
        this.data = data;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        if (viewType == ITEM_AD) {
            View itemView = inflater.inflate(R.layout.item_transparent_ad, parent, false);
            holder = new AdViewHolder(itemView);
        }

        if (viewType == ITEM_CONTENT) {
            View itemView = inflater.inflate(R.layout.item_normal, parent, false);
            holder = new ContentViewHolder(itemView);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position % TYPE_INTERVAL == ITEM_AD) {
            ((ContentViewHolder)holder).tvContent.setText(data.get(position));
        } else {
            System.out.println("xxl: Item_Ad = " + position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % TYPE_INTERVAL == 0) {
            return ITEM_AD;
        } else {
            return ITEM_CONTENT;
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;

        ContentViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    class AdViewHolder extends RecyclerView.ViewHolder {
        AdViewHolder(View itemView) {
            super(itemView);
        }
    }
}
