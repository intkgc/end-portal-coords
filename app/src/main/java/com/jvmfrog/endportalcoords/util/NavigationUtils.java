package com.jvmfrog.endportalcoords.util;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.fragment.NavHostFragment;

import com.jvmfrog.endportalcoords.R;

public class NavigationUtils {

    public static void navigateWithNavHost(FragmentActivity activity, int navHostId, int actionId) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(navHostId);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(actionId, null, new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .setPopUpTo(NavGraph.findStartDestination(navController.getGraph()).getId(),
                        false, // inclusive
                        true) // saveState
                .build());
    }

    public static void navigateWithNavHost(FragmentActivity activity, int navHostId, int actionId, Bundle bundle) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(navHostId);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(actionId, bundle, new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .setPopUpTo(NavGraph.findStartDestination(navController.getGraph()).getId(),
                        false, // inclusive
                        true) // saveState
                .build());
    }

    //navigate with animation
    public static void navigateWithNavHost(FragmentActivity activity, int navHostId, int actionId, Bundle bundle, int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(navHostId);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(actionId, bundle, new NavOptions.Builder()
                .setEnterAnim(enterAnim)
                .setExitAnim(exitAnim)
                .setPopEnterAnim(popEnterAnim)
                .setPopExitAnim(popExitAnim)
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .setPopUpTo(NavGraph.findStartDestination(navController.getGraph()).getId(),
                        false, // inclusive
                        true) // saveState
                .build());
    }

    //open deeplink
    public static void openDeepLink(FragmentActivity activity, String deepLink) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(Uri.parse(deepLink));
    }
}
