package com.jvmfrog.endportalcoords.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.jvmfrog.endportalcoords.EndPortal;
import com.jvmfrog.endportalcoords.Point;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.config.Settings;
import com.jvmfrog.endportalcoords.config.SettingsAssist;
import com.jvmfrog.endportalcoords.databinding.ActivityMainBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.ui.fragment.FirstStepFragment;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File settingsFile = new File(getFilesDir(), "Settings.json");

        if(!settingsFile.exists()) {
            try {
                SettingsAssist.save(settingsFile, Settings.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadSettings();
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new FirstStepFragment()).commit();

        MobileAds.initialize(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        binding.extendedFab.setOnClickListener(
                v -> {
                    Intent intent = new Intent(MainActivity.this, GuideActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_right_to_left);
                }
        );

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.about) {
                Intent intent = new Intent(MainActivity.this, AboutAppActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_right_to_left);
                return true;
            }
            return false;
        });
    }

    public void saveSettings() {
        File settingsFile = new File(getFilesDir(), "Settings.json");

        try {
            SettingsAssist.save(settingsFile, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        File settingsFile = new File(getFilesDir(), "Settings.json");

        try {
            SettingsAssist.load(settingsFile, Settings.class);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}