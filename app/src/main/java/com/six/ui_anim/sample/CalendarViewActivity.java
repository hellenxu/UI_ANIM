package com.six.ui_anim.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.six.ui_anim.R;
import com.six.ui_anim.calendar.CalendarView;
import com.six.ui_anim.calendar.Cell;

/**
 * @author hellenxu
 * @date 2015/9/8
 * Copyright 2015 Six. All rights reserved.
 */

public class CalendarViewActivity extends Activity implements CalendarView.OnCellTouchListener {
    private CalendarView cvBrandDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_calendar);

        cvBrandDiary = (CalendarView) findViewById(R.id.cv_brand);
        cvBrandDiary.setOnCellTouchListener(this);
    }

    @Override
    public void onTouch(Cell targetCell) {
        Toast.makeText(CalendarViewActivity.this, "selected: " + targetCell.getDayOfMonth(), Toast.LENGTH_SHORT).show();
    }
}
