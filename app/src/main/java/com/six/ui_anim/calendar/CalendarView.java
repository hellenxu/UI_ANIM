package com.six.ui_anim.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.MonthDisplayHelper;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.six.ui_anim.R;

import java.util.Calendar;
import java.util.Random;

/**
 * @author hzxuxiaolin
 * @date 2015/9/6
 * Copyright 2015 six.com. All rights reserved.
 */

public class CalendarView extends ImageView {
    private static final String TAG = CalendarView.class.getSimpleName();
    private Drawable mCalendarFrame;
    private Drawable mSelectedCell;
    private Drawable mSelectedCellNoBand;
    private int mCellWidth;
    private int mCellHeight;
    private int mCellMarginLeft;
    private int mCellMarginTop;
    private float mCellTextSize;

    private MonthDisplayHelper mHelper;
    private Cell[][] mCells = new Cell[5][7];
    private OnCellTouchListener mOnCellTouchListener;
    private boolean noBrand;

    //unit: dip
    private static final int DEFAULT_CELL_WIDTH = 34;
    private static final int DEFAULT_CELL_HEIGHT = 69;
    private static final int DEFAULT_CELL_MARGIN_LFET = 0;

    private static final int DEFAULT_CELL_MARGIN_TOP = 0;
    private static final float DEFAULT_CELL_TXT_SIZE = 12.0F;
    private static final int[] SELECTED_CELL_BG = {
            R.drawable.x_selected_day_january,
            R.drawable.x_selected_day_february,
            R.drawable.x_selected_day_march,
            R.drawable.x_selected_day_april,
            R.drawable.x_selected_day_may,
            R.drawable.x_selected_day_june,
            R.drawable.x_selected_day_july,
            R.drawable.x_selected_day_august,
            R.drawable.x_selected_day_september,
            R.drawable.x_selected_day_october,
            R.drawable.x_selected_day_november,
            R.drawable.x_selected_day_december};

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttributes(context);
        initCalendar(context);
    }

    private void parseAttributes(Context ctx) {
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        TypedArray ta = ctx.obtainStyledAttributes(R.styleable.calendar);

        mCellWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(R.styleable.calendar_cell_width, DEFAULT_CELL_WIDTH), dm);
        mCellHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(R.styleable.calendar_cell_height, DEFAULT_CELL_HEIGHT), dm);
        mCellMarginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(R.styleable.calendar_cell_margin_left, DEFAULT_CELL_MARGIN_LFET), dm);
        mCellMarginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(R.styleable.calendar_cell_margin_top, DEFAULT_CELL_MARGIN_TOP), dm);
        mCellTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, ta.getDimension(R.styleable.calendar_cell_txt_size, DEFAULT_CELL_TXT_SIZE), dm);

        ta.recycle();
    }

    private void initCalendar(Context context) {
        Calendar currentDate = Calendar.getInstance();
        mHelper = new MonthDisplayHelper(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH));

        setImageResource(R.drawable.x_brand_bg_no_brand);
        mCalendarFrame = context.getResources().getDrawable(R.drawable.x_brand_calenday_bg);
        mSelectedCell = context.getResources().getDrawable(SELECTED_CELL_BG[mHelper.getMonth()]);
        mSelectedCellNoBand = context.getResources().getDrawable(R.drawable.x_selected_day);
        setNoBrand(new Random().nextBoolean());
    }

    //初始化当前显示月份的格子
    private void initCells() {
        class calendar {
            int day;
            boolean isWithinMonth;

            calendar(int day, boolean isWithinMonth) {
                this.day = day;
                this.isWithinMonth = isWithinMonth;
            }
        }

        //保存当前月份每天的信息，方便下面的Cell[5][7]初始化
        calendar[][] tmp = new calendar[5][7];

        for (int row = 0; row < tmp.length; row++) {
            int[] columns = mHelper.getDigitsForRow(row);
            for (int day = 0; day < columns.length; day++) {
                tmp[row][day] = new calendar(columns[day], mHelper.isWithinCurrentMonth(row, day));
            }
        }

        //初始化准备工作：1) 重新取Calendar实例，取得today的值; [原因: MonthDisplayHelper需要row和column的参数才可以取得day of month]
        // 2)初始化Cell[5][7];
        Calendar calToday = Calendar.getInstance();
        int today = 0;
        if (calToday.get(Calendar.YEAR) == mHelper.getYear() && calToday.get(Calendar.MONTH) == mHelper.getMonth()) {
            today = calToday.get(Calendar.DAY_OF_MONTH);
        }

//        mCells = null;
        Rect cellBound = new Rect(mCellMarginLeft, mCellMarginTop, mCellMarginLeft + mCellWidth, mCellMarginTop + mCellHeight);
        for (int week = 0; week < mCells.length; week++) {
            for (int day = 0; day < mCells[week].length; day++) {
                if (tmp[week][day].isWithinMonth && tmp[week][day].day == today) {
                    mCells[week][day] = new WhiteCell(tmp[week][day].day, cellBound, mCellTextSize, true);

                    Rect selectedRect = new Rect(cellBound.left - mCellMarginLeft, cellBound.top - mCellMarginTop, cellBound.right - mCellMarginLeft, cellBound.bottom - mCellMarginTop);
                    mSelectedCell.setBounds(selectedRect);
                    mSelectedCellNoBand.setBounds(selectedRect);
                } else {
                    mCells[week][day] = new Cell(tmp[week][day].day, cellBound, mCellTextSize, false);
                }
                cellBound.offset(mCellWidth, 0); //同一排，y的坐标不变
            }
            //重设下一排绘制区域的位置，包括偏移坐标、margin等
            cellBound.offset(0, mCellHeight);
            cellBound.left = mCellMarginLeft;
            cellBound.right = mCellMarginLeft + mCellWidth;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mCalendarFrame.setBounds(getDrawable().getBounds());
        initCells();
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCalendarFrame.draw(canvas);

        if (noBrand) {
            mSelectedCellNoBand.draw(canvas);
        } else {
            mSelectedCell.draw(canvas);
        }

        for (Cell[] week : mCells) {
            for (Cell day : week) {
                day.draw(canvas);
            }
        }
    }

    public void setNoBrand(boolean noBrand) {
        this.noBrand = noBrand;
    }

    public void setOnCellTouchListener(OnCellTouchListener listener) {
        this.mOnCellTouchListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (mOnCellTouchListener != null) {
            for (Cell[] week : mCells) {
                for (Cell day : week) {
                    if (day.hitCell(x, y)) {
                        mOnCellTouchListener.onTouch(day);
                    }
                }
            }
        }

        return super.onTouchEvent(event);
    }

    //selected cell
    public class WhiteCell extends Cell {

        public WhiteCell(int dayOfMonth, Rect rect, float textSize, boolean isBold) {
            super(dayOfMonth, rect, textSize, isBold);
            mPaint.setColor(Color.WHITE);
        }
    }

    public interface OnCellTouchListener {
        void onTouch(Cell targetCell);
    }
}
