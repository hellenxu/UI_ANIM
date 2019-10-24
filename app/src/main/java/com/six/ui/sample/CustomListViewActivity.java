package com.six.ui.sample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.ListView;

import com.six.ui.customlistview.GridListViewAdapter;
import com.six.ui.customlistview.Product;
import com.six.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hellenxu
 * @date 2017-09-22
 * Copyright 2017 Six. All rights reserved.
 */

public class CustomListViewActivity extends Activity {
    private ListView productsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_custom_listview);
        productsListView = (ListView) findViewById(R.id.product_list);
        new GetData().execute();
    }

    private final class GetData extends AsyncTask<Void, Integer, Void> {
        private List<Product> products = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... params) {
            for(int i = 0; i < 40; i ++){
                Product product;
                if(i % 5 == 0){
                    product = new Product(true, "product" + i);
                }else {
                    product = new Product(false, "item " + i);
                }
                products.add(product);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productsListView.setAdapter(new GridListViewAdapter(CustomListViewActivity.this, products));
        }
    }
}
