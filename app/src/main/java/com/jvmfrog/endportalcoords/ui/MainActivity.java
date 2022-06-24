package com.jvmfrog.endportalcoords.ui;

import static com.jvmfrog.endportalcoords.util.FragmentUtils.changeFragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.ActivityMainBinding;
import com.jvmfrog.endportalcoords.ui.fragment.AboutFragment;
import com.jvmfrog.endportalcoords.ui.fragment.EndPortalFinderFragment;
import com.jvmfrog.endportalcoords.ui.fragment.GuideFragment;
import com.jvmfrog.endportalcoords.ui.fragment.HistoryFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    String[] data = {};
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changeFragment(this, new EndPortalFinderFragment(), R.id.frame, null);

        //Переключатель для нижнего бара
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calculate:
                    changeFragment(this, new EndPortalFinderFragment(), R.id.frame, null);
                    break;
                case R.id.history:
                    changeFragment(this, new HistoryFragment(), R.id.frame, null);
                    break;
                case R.id.guide:
                    changeFragment(this, new GuideFragment(), R.id.frame, null);
                    break;
                case R.id.about:
                    changeFragment(this, new AboutFragment(), R.id.frame, null);
                    break;
            }
            return true;
        });
    }
}