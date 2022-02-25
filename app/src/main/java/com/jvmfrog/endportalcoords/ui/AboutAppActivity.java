package com.jvmfrog.endportalcoords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.jvmfrog.endportalcoords.BuildConfig;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.api.ChromeCustomTabAPI;

public class AboutAppActivity extends AppCompatActivity {

    private TextView version;

    private MaterialButton source_code_btn, kirill_btn, ibragim_btn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        Init();

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        version.setText(getString(R.string.version) + ":" + " " + BuildConfig.VERSION_NAME + " " + "(" + BuildConfig.VERSION_CODE + ")");

        final ChromeCustomTabAPI chromeCustomTabAPI = new ChromeCustomTabAPI(AboutAppActivity.this);

        source_code_btn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.source_code_url), R.color.white));

        kirill_btn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.kirill_url), R.color.white));

        ibragim_btn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(AboutAppActivity.this, getString(R.string.ibragim_url), R.color.white));
    }

    public void Init() {

        version = findViewById(R.id.version_text);

        source_code_btn = findViewById(R.id.source_code_btn);
        kirill_btn = findViewById(R.id.kirill_btn);
        ibragim_btn = findViewById(R.id.ibragim_btn);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}