package com.six.ui.tabLayout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.six.ui.R;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */

public class IndexFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        LinearLayout llay = new LinearLayout(getActivity());
        llay.setOrientation(LinearLayout.VERTICAL);
        TextView titleTextView = new TextView(getActivity());
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        titleTextView.setText(R.string.txt_index);
//        Button btn = new Button(getActivity());
//        btn.setText(R.string.txt_launch_dialog);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).showSurveyDialog();
//            }
//        });
        llay.addView(titleTextView);
//        llay.addView(btn);
        System.out.println("xxl-onCreateView");
        return llay;
    }
}
