package com.six.ui_anim.action_sheet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2017-09-12
 * Copyright 2017 Six. All rights reserved.
 */

public class ActionSheetDialog extends Activity implements View.OnClickListener {

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
}
