package com.six.ui.tabLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */

public class SurveyDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        builder.setTitle("Survey")
                .setMessage("Would you like to give us feedback?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null);
        Dialog dialog = builder.create();
        // not going to be useful, since DialogFragment change behaviours based on this
//        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
