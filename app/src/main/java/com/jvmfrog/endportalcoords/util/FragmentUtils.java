package com.jvmfrog.endportalcoords.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jvmfrog.endportalcoords.R;

public class FragmentUtils {
    public static void changeFragment(FragmentActivity activity, Fragment to, int frameId, Bundle bundle) {
        to.setArguments(bundle);
        defaultFragmentTranslation(activity, to, frameId).commit();
    }

    public static void changeFragmentWithAnimation(FragmentActivity activity, Fragment to, int frameId, Bundle bundle) {
        to.setArguments(bundle);
        FragmentTransaction transaction = defaultFragmentTranslation(activity, to, frameId);
        transaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                R.anim.enter_right_to_left, R.anim.exit_right_to_left).commit();
    }

    private static FragmentTransaction defaultFragmentTranslation(FragmentActivity activity, Fragment to, int frameId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameId, to);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        return fragmentTransaction;
    }
}
