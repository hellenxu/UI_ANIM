package com.six.ui.sample.colors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.six.ui.R;

/**
 * @author hellenxu
 * @date 2016/3/14
 * Copyright 2016 Six. All rights reserved.
 */
public class ColorMatrixSample extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar sbHue, sbSat, sbLum;
    private ImageView ivSample;
    private float mHue, mSaturation, mLum;
    private Bitmap bitmap;
    private static final int MAX_VALUE = 255;
    private static final int MID_VALUE = 127;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_matrix);
        initView();
    }

    private void initView() {
        sbHue = (SeekBar) findViewById(R.id.sb_hue);
        sbSat = (SeekBar) findViewById(R.id.sb_sat);
        sbLum = (SeekBar) findViewById(R.id.sb_Lum);

        sbHue.setOnSeekBarChangeListener(this);
        sbSat.setOnSeekBarChangeListener(this);
        sbLum.setOnSeekBarChangeListener(this);


//        sbHue.setProgress(MID_VALUE);
//        sbSat.setProgress(MID_VALUE);
//        sbLum.setProgress(MID_VALUE);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_matrix);
        ivSample = (ImageView) findViewById(R.id.iv_sample);
        ivSample.setImageBitmap(bitmap);

        sbHue.setMax(MAX_VALUE);
        sbSat.setMax(MAX_VALUE);
        sbLum.setMax(MAX_VALUE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_hue:
                mHue = (progress - MID_VALUE) * 1.0f / MID_VALUE * 180;
                break;
            case R.id.sb_sat:
                mSaturation = progress * 1.0f / MID_VALUE;
                break;
            case R.id.sb_Lum:
                mLum = progress * 1.0f / MID_VALUE;
                break;
            default:
                break;
        }
        ivSample.setImageBitmap(ImageHelper.handleImageEffect(bitmap, mHue, mSaturation, mLum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
