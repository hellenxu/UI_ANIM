package com.six.ui.tabLayout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */

public class SurveyDialog extends DialogFragment {
    private Dialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        builder.setTitle("Survey")
                .setMessage("Would you like to give us feedback?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null);
        dialog = builder.create();
        // not going to be useful, since DialogFragment change behaviours based on this
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        System.out.println("xxl-oncreatedialog: " + getDialog());
        System.out.println("xxl- dialogFrag = " + isCancelable());
        System.out.println("xxl- oncreatedialog = " + dialog);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("xxl-onAttach: " + getDialog());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("xxl-oncreate: " + getDialog());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("xxl-oncreateview: " + getDialog());
        System.out.println("xxl- oncreateview = " + isCancelable());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("xxl-onActivity: " + getDialog());
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("xxl-onstart: " + getDialog());
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("xxl-onresume: " + getDialog());
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("xxl-onpause: " + getDialog());
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("xxl-onStop: " + getDialog());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("xxl-onDestroyView: " + getDialog());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("xxl-ondestroy: " + getDialog());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("xxl-ondetach: " + getDialog());
    }
}
