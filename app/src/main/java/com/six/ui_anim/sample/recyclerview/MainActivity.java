package com.six.ui_anim.sample.recyclerview;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.six.ui_anim.R;
import com.six.ui_anim.recyclerView.GridDividerDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author hellenxu
 * @date 2015/7/8
 * Copyright 2015 Six. All rights reserved.
 */

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mData;
    private RVAdapter mAdapter;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recycler);
        initData();
        initView();
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            mData.add("" + (char) i);
        }
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.tl_top);
        setSupportActionBar(mToolBar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);

        mAdapter = new RVAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_data);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new GridDividerDecoration(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rv_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
                mAdapter.addData(1);
                break;
            case R.id.item_delete:
                mAdapter.removeData(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVHolder> {
        private int[] heights = new int[mData.size()];

        public RVAdapter() {
            Random rd = new Random();
            for (int i = 0; i < mData.size(); i++) {
                heights[i] = rd.nextInt(300) + 100;
            }
        }

        @Override
        public RVHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new RVHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.rv_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(RVHolder rvHolder, int i) {
            rvHolder.tvItem.setText(mData.get(i));
            rvHolder.tvItem.setHeight(heights[i]);
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

        public class RVHolder extends RecyclerView.ViewHolder {
            TextView tvItem;

            public RVHolder(View itemView) {
                super(itemView);
                tvItem = (TextView) itemView.findViewById(R.id.tv_item);
            }
        }
    }
}
