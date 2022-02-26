package com.jvmfrog.endportalcoords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.jvmfrog.endportalcoords.R;
import com.shuhart.stepview.StepView;

public class GuideActivity extends AppCompatActivity {

    private StepView stepView;

    private TextView guide_title, guide_description;
    private ImageView guide_image;

    int stepIndex = 0;
    private String[] guideTitle;
    private String[] guideDescription;
    private Drawable[] guideImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        guideTitle = new String[] {
                getString(R.string.guide_title_where_are_coords_and_angle_throw),
                getString(R.string.debug_menu),
                getString(R.string.guide_title_coords_xz),
                getString(R.string.guide_title_throw_angle)
        };

        guideDescription = new String[] {
                getString(R.string.guide_key_f),
                getString(R.string.guide_if_debug_menu_opened),
                getString(R.string.guide_coords_xz),
                getString(R.string.guide_throw_angle)
        };

        guideImage = new Drawable[] {
                getDrawable(R.drawable.key_f3), getDrawable(R.drawable.debug_menu),
                getDrawable(R.drawable.debug_menu_xz), getDrawable(R.drawable.debug_menu_throw_angle)
        };

        stepView = findViewById(R.id.step_view);

        guide_title = findViewById(R.id.guide_title);
        guide_description = findViewById(R.id.guide_description);
        guide_image = findViewById(R.id.guide_image);

        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        guide();
    }

    public void guide() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepIndex++;

                if (stepIndex < guideTitle.length) {
                    guide_title.setText(guideTitle[stepIndex]);
                    guide_description.setText(guideDescription[stepIndex]);
                    guide_image.setImageDrawable(guideImage[stepIndex]);
                    stepView.go(stepIndex, true);
                    guide();
                } else {
                    finish();
                }
            }
        }, 4000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}