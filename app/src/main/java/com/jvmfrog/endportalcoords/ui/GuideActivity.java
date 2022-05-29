package com.jvmfrog.endportalcoords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.ActivityGuideBinding;
import com.shuhart.stepview.StepView;

public class GuideActivity extends AppCompatActivity {

    ActivityGuideBinding binding;

    int stepIndex = 0;
    private String[] guideTitle;
    private String[] guideDescription;
    private Drawable[] guideImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuideBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

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

        binding.stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        binding.extendedFab.setOnClickListener(
                v -> {
                    onBackPressed();
                }
        );

        guide();
    }

    public void guide() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepIndex++;

                if (stepIndex < guideTitle.length) {
                    binding.guideTitle.setText(guideTitle[stepIndex]);
                    binding.guideDescription.setText(guideDescription[stepIndex]);
                    binding.guideImage.setImageDrawable(guideImage[stepIndex]);
                    binding.stepView.go(stepIndex, true);
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_left_to_right, R.anim.exit_left_to_right);
    }
}