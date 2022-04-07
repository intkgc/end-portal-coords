package com.jvmfrog.endportalcoords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.jvmfrog.endportalcoords.BuildConfig;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.api.ChromeCustomTabAPI;
import com.jvmfrog.endportalcoords.databinding.ActivityAboutAppBinding;

public class AboutAppActivity extends AppCompatActivity {

    ActivityAboutAppBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutAppBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.versionText.setText(getString(R.string.version) + ":" + " " + BuildConfig.VERSION_NAME + " " + "(" + BuildConfig.VERSION_CODE + ")");

        final ChromeCustomTabAPI chromeCustomTabAPI = new ChromeCustomTabAPI(AboutAppActivity.this);

        binding.sourceCodeBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.source_code_url), R.color.white));
        binding.kirillBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.kirill_url), R.color.white));
        binding.ibragimBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.ibragim_url), R.color.white));
        binding.translateAppBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.crowdin_project_url), R.color.white));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}