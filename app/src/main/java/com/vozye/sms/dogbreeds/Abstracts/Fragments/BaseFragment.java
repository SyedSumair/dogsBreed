package com.vozye.sms.dogbreeds.Abstracts.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.vozye.sms.dogbreeds.DogsMainActivity;

/**
 * Created by sumair on 12/16/2017.
 */

public abstract class BaseFragment extends Fragment{


    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    protected void showLoader() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setMessage("Loading please wait... ");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    protected void hideLoader() {
        if (progressDialog != null) {
            progressDialog.hide();
            progressDialog = null;
        }

    }
}