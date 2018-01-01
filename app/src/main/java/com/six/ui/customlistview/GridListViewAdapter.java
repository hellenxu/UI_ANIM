package com.six.ui.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.six.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hellenxu
 * @date 2017-09-22
 * Copyright 2017 Six. All rights reserved.
 */

public class GridListViewAdapter extends BaseAdapter {
    private List<Product> products = new ArrayList<>();
    private List<Integer> firstItemIndex = new ArrayList<>(); //save product index for first item of every row
    private LayoutInflater layoutInflater;
    private int lineCount = 0;
    private int maxColumn = 2;
    private int sameLineCursor = 0;
    private int productsCount = 0;

    private static final int TYPE_COUNT = 2;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int[] logoResIds = {R.drawable.if_android, R.drawable.if_dribbble,
            R.drawable.if_evernote, R.drawable.if_github, R.drawable.if_instagram, R.drawable.if_quora,
            R.drawable.if_skype, R.drawable.if_snapchat, R.drawable.if_vimeo, R.drawable.if_vkontakte};

    public GridListViewAdapter(Context ctx, List<Product> data) {
        layoutInflater = LayoutInflater.from(ctx);
        products = data;
        organizeData();
        System.out.println("xxl-products: " + products.size());
        System.out.println("xxl-lines: " + lineCount);
    }

    private void organizeData() {
        productsCount = products.size();
        List<Integer> tmp = new ArrayList<>(); // save line index for each product.

        for (Product product : products) {
            if (product.isHeader) {
                if (sameLineCursor != 0) { // this branch handles previous row that's not populated fully.
                    sameLineCursor = 0; // reset cursor
                    lineCount++; // increase line count
                }
                tmp.add(lineCount);
                lineCount++;
            } else {
                tmp.add(lineCount);
                sameLineCursor++;
                if (sameLineCursor >= maxColumn) { // a row is fully populated, so start a new row.
                    lineCount++;
                    sameLineCursor = 0;
                }
            }
        }

        firstItemIndex.clear();
        int previousPos = -1;
        for (int i = 0; i < tmp.size(); i++) {
            int currentPos = tmp.get(i);
            if (currentPos != previousPos) {
                firstItemIndex.add(i);
                previousPos = currentPos;
            }
        }
        lineCount = firstItemIndex.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        organizeData();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return firstItemIndex.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int getProductIndexFromPosition(int pos) {
        return firstItemIndex.get(pos);
    }

    @Override
    public int getItemViewType(int position) {
        int index = getProductIndexFromPosition(position);
        if (products.get(index).isHeader) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int index = getProductIndexFromPosition(position);
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ProductHeaderViewHolder headerVH;
                if (convertView == null) {
                    headerVH = new ProductHeaderViewHolder();
                    convertView = layoutInflater.inflate(R.layout.item_header, parent, false);
                    headerVH.groupName = (TextView) convertView.findViewById(R.id.group_title);
                    convertView.setTag(headerVH);
                } else {
                    headerVH = (ProductHeaderViewHolder) convertView.getTag();
                }
                headerVH.groupName.setText(products.get(index).productName);
                break;

            case TYPE_ITEM:
                ProductItemViewHolder itemVH;
                if (convertView == null) {
                    itemVH = new ProductItemViewHolder();
                    convertView = layoutInflater.inflate(R.layout.item_product, parent, false);
                    itemVH.leftLogo = (ImageView) convertView.findViewById(R.id.logo_left);
                    itemVH.productNameLeft = (TextView) convertView.findViewById(R.id.name_left);
                    itemVH.rightLogo = (ImageView) convertView.findViewById(R.id.logo_right);
                    itemVH.productNameRight = (TextView) convertView.findViewById(R.id.name_right);
                    convertView.setTag(itemVH);
                } else {
                    itemVH = (ProductItemViewHolder) convertView.getTag();
                }

                itemVH.productNameLeft.setText(products.get(index).productName);
                itemVH.leftLogo.setImageResource(logoResIds[index%logoResIds.length]);
                final int nextIndex = index + 1;
                if (nextIndex < productsCount) {
                    Product nextProduct = products.get(nextIndex);

                    if (nextProduct.isHeader) {
                        itemVH.rightLogo.setVisibility(View.INVISIBLE);
                        itemVH.productNameRight.setVisibility(View.INVISIBLE);
                    } else {
                        itemVH.rightLogo.setVisibility(View.VISIBLE);
                        itemVH.productNameRight.setVisibility(View.VISIBLE);
                        itemVH.rightLogo.setImageResource(logoResIds[nextIndex%logoResIds.length]);
                        itemVH.productNameRight.setText(products.get(nextIndex).productName);
                    }
                } else {
                    itemVH.rightLogo.setVisibility(View.INVISIBLE);
                    itemVH.productNameRight.setVisibility(View.INVISIBLE);
                }

                break;
        }
        return convertView;
    }

    private final class ProductHeaderViewHolder {
        TextView groupName;
    }

    private final class ProductItemViewHolder {
        TextView productNameLeft, productNameRight;
        ImageView leftLogo, rightLogo;
    }
}
