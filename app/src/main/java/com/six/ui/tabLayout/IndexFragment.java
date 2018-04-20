package com.six.ui.tabLayout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.six.ui.R;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */

public class IndexFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TextView titleTextView = new TextView(getActivity());
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        titleTextView.setText(R.string.txt_index);
        System.out.println("xxl-onCreateView");
        return titleTextView;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("xxl-onStart");
        showDialog();
    }

    private void showDialog() {
        SurveyDialog dialog = new SurveyDialog();
        dialog.show(getFragmentManager(), "SurveyDialog");
    }
}
