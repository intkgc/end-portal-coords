package com.jvmfrog.endportalcoords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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

        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        final ChromeCustomTabAPI chromeCustomTabAPI = new ChromeCustomTabAPI(AboutAppActivity.this);

        binding.appVersionBtn.setText(getString(R.string.version) + ": " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");
        binding.sourceCodeBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.source_code_url), R.color.white));
        binding.kirillBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.kirill_url), R.color.white));
        binding.ibragimBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.ibragim_url), R.color.white));
        binding.vkGroupBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.JVMFrog), R.color.white));
        binding.otherAppsBtn.setOnClickListener(view1 -> {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:JVMFrog")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.GOOGLE_PLAY))));
            }
        });
        binding.translateAppBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.crowdin_project_url), R.color.white));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_left_to_right, R.anim.exit_left_to_right);
    }
}