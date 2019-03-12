package com.six.ui.tabLayout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;

import com.six.ui.R;
import com.six.ui.seekbar.SixProgressbar;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */

public class TravelFragment extends Fragment implements View.OnClickListener {
    private SixProgressbar usage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        TextView titleTextView = new TextView(getActivity());
//        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//        titleTextView.setText(R.string.txt_travel);
//        return titleTextView;

        View root = inflater.inflate(R.layout.frag_travel, container, false);
        usage = root.findViewById(R.id.usage);
        usage.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        usage.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
    }
}
