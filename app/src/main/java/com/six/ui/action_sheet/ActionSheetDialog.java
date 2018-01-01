package com.six.ui.action_sheet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2017-09-12
 * Copyright 2017 Six. All rights reserved.
 */

public class ActionSheetDialog extends Activity implements View.OnClickListener {
    private FrameLayout contentView;
    private View actionSheet;
    private boolean isBackPressed = true;

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
        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        LinearLayout subView = (LinearLayout) decorView.getChildAt(0);
        contentView = (FrameLayout) subView.findViewById(android.R.id.content);

        actionSheet = LayoutInflater.from(this).inflate(R.layout.action_sheet, null, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1, Gravity.BOTTOM);
        actionSheet.setLayoutParams(params);
        actionSheet.setAnimation(AnimationUtils.loadAnimation(this, R.anim.action_sheet_enter));

        contentView.addView(actionSheet);
        isBackPressed = false;
    }

    @Override
    public void onBackPressed() {
        if(contentView != null) {
            actionSheet.setAnimation(AnimationUtils.loadAnimation(this, R.anim.action_sheet_exit));
            contentView.removeView(actionSheet);
        }

        if(isBackPressed) {
            super.onBackPressed();
        }
        isBackPressed = true;
    }
}
