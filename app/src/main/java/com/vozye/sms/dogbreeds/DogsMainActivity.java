package com.vozye.sms.dogbreeds;

import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vozye.sms.dogbreeds.Abstracts.Activities.BaseActivity;
import com.vozye.sms.dogbreeds.Abstracts.Fragments.BreedsListingFragment;
import com.vozye.sms.dogbreeds.Abstracts.onFragmentChangeCallback;

public class DogsMainActivity extends BaseActivity implements onFragmentChangeCallback {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_listing);
        initFragments();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }


    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }



    public void initFragments() {

        addDockableFragment(BreedsListingFragment.newInstance());

    }


    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void onFragmentChange(String FragmentTitle) {
        toolbar.setTitle(FragmentTitle);
    }
}
