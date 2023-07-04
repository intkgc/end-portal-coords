package com.jvmfrog.endportalcoords.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.ActivityMainBinding;
import com.jvmfrog.endportalcoords.util.NavigationUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavigationUtils.navigateWithNavHost(this, R.id.nav_host_fragment, R.id.mainFragment, null);
    }
}