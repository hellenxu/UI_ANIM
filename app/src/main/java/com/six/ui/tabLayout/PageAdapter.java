package com.six.ui.tabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.util.SparseArray;


/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */

public class PageAdapter extends FragmentPagerAdapter {
    private int num;
    private SparseArray<Fragment> fragmentSparseArray = new SparseArray<>();

    public PageAdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int position) {
        return createFragment(position);
    }

    @Override
    public int getCount() {
        return num;
    }

    private Fragment createFragment(int pos) {
        Fragment fragment = fragmentSparseArray.get(pos);

        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = new IndexFragment();
                    System.out.println("xxl: create index");
                    break;
                case 1:
                    fragment = new TravelFragment();
                    System.out.println("xxl: create travel");
                    break;
                case 2:
                    fragment = new OffersFragment();
                    System.out.println("xxl: create offers");
                    break;
                case 3:
                    fragment = new DetailsFragment();
                    System.out.println("xxl: create details");
                    break;
            }

            fragmentSparseArray.put(pos, fragment);
        }

        return fragment;
    }
}
