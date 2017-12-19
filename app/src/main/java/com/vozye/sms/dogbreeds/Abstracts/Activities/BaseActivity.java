package com.vozye.sms.dogbreeds.Abstracts.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.vozye.sms.dogbreeds.Abstracts.Fragments.BaseFragment;
import com.vozye.sms.dogbreeds.R;

/**
 * Created by sumair on 12/16/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static final String KEY_FRAG_FIRST = "firstFrag";
    protected BaseFragment baseFragment;

    public void addDockableFragment(BaseFragment frag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        baseFragment = frag;
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(getDockFrameLayoutId(), frag, frag.getClass().getSimpleName());
        transaction.addToBackStack(getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST : null).commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    public void emptyBackStack() {
        popBackStackTillEntry(0);
    }

    public void popFragment() {
        if (getSupportFragmentManager() == null)
            return;
        getSupportFragmentManager().popBackStack();
    }

    public void popBackStackTillEntry(int entryIndex) {
        if (getSupportFragmentManager() == null)
            return;
        if (getSupportFragmentManager().getBackStackEntryCount() <= entryIndex)
            return;
        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
                entryIndex);
        if (entry != null) {
            getSupportFragmentManager().popBackStack(entry.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public BaseFragment getLastAddedFragment() {
        return baseFragment;
    }

    public abstract int getDockFrameLayoutId();
}