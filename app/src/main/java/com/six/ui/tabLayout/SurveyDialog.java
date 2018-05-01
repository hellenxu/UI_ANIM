package com.six.ui.tabLayout;

import android.app.Dialog;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */

public class SurveyDialog extends DialogFragment implements DialogInterface.OnClickListener, Survey.View {
    private final FragmentManager manager;
    //    private Dialog dialog;
    private SurveyDialogPresenter presenter;
    private String title;
    private String message;
    private String positiveLabel;
    private String neutralLabel;
    private String negativeLabel;

    public SurveyDialog(FragmentManager manager) {
        this.manager = manager;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        presenter = new SurveyDialogPresenter();
        presenter.setSurveyView(this);

        setCancelable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveLabel, this)
                .setNeutralButton(neutralLabel, this)
                .setNegativeButton(negativeLabel, this);

        System.out.println("xxl-oncreatedialog: " + getDialog());
        System.out.println("xxl- dialogFrag = " + isCancelable());

        return builder.create();
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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                presenter.handlePositive();
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                presenter.handleNeutral();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                presenter.handleNegative();
                break;
        }
    }

    private void setupTwoButtonDialog() {
        title = "Survey";
        message = "Would you like to give us feedback?";
        positiveLabel = "Yes";
        negativeLabel = "No";

    }

    private void setupThreeButtonDialog() {
        title = "Survey";
        message = "Would you like give us a rate in play store?";
        positiveLabel = "Yes";
        neutralLabel = "Not Now";
        negativeLabel = "No";

    }

    @Override
    public void showThreeButtonDialog() {
        SurveyDialog dialog = new SurveyDialog(manager);
        dialog.setupThreeButtonDialog();
        dialog.show(manager, "3btnDialog");
        FragmentTransaction ft = manager.beginTransaction();
        ft.detach(manager.findFragmentByTag("2btnDialog"));
        ft.commit();
    }

    @Override
    public void showTwoButtonDialog() {
//        SurveyDialog dialog = new SurveyDialog(manager);
        this.setupTwoButtonDialog();
//        System.out.println("xxl-fragment manager: " + manager);
        this.show(manager, "2btnDialog");
    }
}
