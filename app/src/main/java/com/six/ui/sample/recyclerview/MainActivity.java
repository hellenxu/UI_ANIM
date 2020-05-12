package com.six.ui.sample.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.six.ui.recyclerview.GridDividerDecoration;
import com.six.ui.recyclerview.LeftAlignSnapHelper;
import com.six.ui.recyclerview.RVAdapter;
import com.six.ui.recyclerview.RvItemCallback;
import com.six.ui.recyclerview.RvItemClickListener;
import com.six.ui.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author hellenxu
 * @date 2015/7/8
 * Copyright 2015 Six. All rights reserved.
 */

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private List<String> mData;
    private RVAdapter mAdapter;

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
        mAdapter = new RVAdapter(this, mData);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_data);
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new GridDividerDecoration(this));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RvItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Toast.makeText(mRecyclerView.getContext(), "pos: " + vh.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RvItemCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

//        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(mRecyclerView);
        LeftAlignSnapHelper leftSnapHelper =  new LeftAlignSnapHelper();
        leftSnapHelper.attachToRecyclerView(mRecyclerView);
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
}
