package com.six.ui_anim.action_sheet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2017-09-12
 * Copyright 2017 Six. All rights reserved.
 */

public class ActionSheetDialog extends Activity implements View.OnClickListener {
    private FrameLayout decorView;
    private View actionSheet;
    private boolean isRemoveActionSheet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_action);

        Button btnDialog = (Button) findViewById(R.id.btn_dialog);
        Button btnDecor = (Button) findViewById(R.id.btn_decor);

        btnDialog.setOnClickListener(this);
        btnDecor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog:
                showDialog();
                break;
            case R.id.btn_decor:
                useDecorView();
                break;
        }
    }

    private void showDialog(){
        Dialog dialog = new Dialog(this, R.style.ActionSheet);
        dialog.setContentView(R.layout.action_sheet);
        final Window dialogWindow = dialog.getWindow();
        if(dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setLayout(-1, -2);
        }
        dialog.show();
    }

    private void useDecorView() {
        decorView = (FrameLayout) getWindow().getDecorView();
        int navBarHeight = decorView.getChildAt(1).getHeight();
        actionSheet = LayoutInflater.from(this).inflate(R.layout.action_sheet, null, false);
        isRemoveActionSheet = false;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -2, Gravity.BOTTOM);
        params.setMargins(0, 0, 0, navBarHeight);
        actionSheet.setLayoutParams(params);
        actionSheet.setAnimation(AnimationUtils.loadAnimation(this, R.anim.action_sheet_enter));
        decorView.addView(actionSheet, decorView.getChildCount());
    }

    @Override
    public void onBackPressed() {
        if(decorView != null) {
            actionSheet.setAnimation(AnimationUtils.loadAnimation(this, R.anim.action_sheet_exit));
            decorView.removeView(actionSheet);
        }

        if(isRemoveActionSheet) {
            super.onBackPressed();
        }
        isRemoveActionSheet = true;
    }
}
