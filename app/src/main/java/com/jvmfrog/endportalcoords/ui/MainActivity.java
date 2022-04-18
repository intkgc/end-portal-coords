package com.jvmfrog.endportalcoords.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

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

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private ReviewInfo reviewInfo;
    private ReviewManager reviewManager;

    private float first_x, first_z, second_x, second_z, first_ta, second_ta;

    @SuppressLint("SetTextI18n")
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

        activateReviewInfo();

        binding.firstStepBtn.setOnClickListener(
                v -> {
                    if (!binding.firstXCoord.getText().toString().isEmpty() &&
                            !binding.firstZCoord.getText().toString().isEmpty() &&
                            !binding.firstThrowAngle.getText().toString().isEmpty()) {

                        binding.firstStep.setVisibility(View.GONE);
                        binding.nextStep.setVisibility(View.VISIBLE);
                        binding.finishStep.setVisibility(View.GONE);
                        binding.stepView.go(1, true);

                    } else {
                        System.out.println("Error fields must be filled");
                        Dialogs.checkAllFields(MainActivity.this);
                    }
                });

        binding.secondStepBtn.setOnClickListener(
                v -> {
                    if (!binding.secondXCoord.getText().toString().isEmpty() &&
                            !binding.secondZCoord.getText().toString().isEmpty() &&
                            !binding.secondThrowAngle.getText().toString().isEmpty()) {

                        first_x = Float.parseFloat(binding.firstXCoord.getText().toString());
                        first_z = Float.parseFloat(binding.firstZCoord.getText().toString());
                        first_ta = Float.parseFloat(binding.firstThrowAngle.getText().toString());

                        second_x = Float.parseFloat(binding.secondXCoord.getText().toString());
                        second_z = Float.parseFloat(binding.secondZCoord.getText().toString());
                        second_ta = Float.parseFloat(binding.secondThrowAngle.getText().toString());

                        try {
                            Point endPortal = EndPortal.getPortalCoords(new Point(first_x, first_z), new Point(second_x, second_z), first_ta, second_ta);
                            System.out.println(endPortal.x + " " + endPortal.z);

                            binding.portalCoords.setText("X: " + (int) endPortal.x + " × " + "Z: " + (int) endPortal.z);

                            binding.firstStep.setVisibility(View.GONE);
                            binding.nextStep.setVisibility(View.GONE);
                            binding.finishStep.setVisibility(View.VISIBLE);
                            binding.stepView.go(2, true);
                            binding.stepView.done(true);

                            if(Settings.feedbackCounter == 5 && Settings.isFeedbackShowed == false) {
                                Settings.isFeedbackShowed = true;
                                saveSettings();
                                startReviewFlow();
                            } else {
                                Settings.feedbackCounter += 1;
                                saveSettings();
                            }

                            //Dialogs.endPortalCoordinates(this, endPortal.x, endPortal.z).show();
                        } catch (AnglesEqualException e) {
                            Dialogs.angleEqualException(MainActivity.this);
                        } catch (AnglesOppositeException e) {
                            Dialogs.angleOppositeException(MainActivity.this);
                        }
                    } else {
                        System.out.println("Error fields must be filled");
                        Dialogs.checkAllFields(MainActivity.this);
                    }
                }
        );

        binding.finishStepBtn.setOnClickListener(
                v -> {
                    binding.firstXCoord.getText().clear();
                    binding.firstZCoord.getText().clear();
                    binding.firstThrowAngle.getText().clear();

                    binding.secondXCoord.getText().clear();
                    binding.secondZCoord.getText().clear();
                    binding.secondThrowAngle.getText().clear();

                    binding.firstStep.setVisibility(View.VISIBLE);
                    binding.nextStep.setVisibility(View.GONE);
                    binding.finishStep.setVisibility(View.GONE);
                    binding.stepView.go(0,true);
                    binding.stepView.done(false);
                }
        );

        binding.extendedFab.setOnClickListener(
                v -> {
                    Intent intent = new Intent(MainActivity.this, GuideActivity.class);
                    startActivity(intent);
                }
        );

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.about) {
                Intent intent = new Intent(MainActivity.this, AboutAppActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    void activateReviewInfo() {
        reviewManager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> managerInfoTask = reviewManager.requestReviewFlow();
        managerInfoTask.addOnCompleteListener((task)->{
            if(task.isSuccessful()) {
                reviewInfo = task.getResult();
            } else {
                Toast.makeText(this, "Review failed to start", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void startReviewFlow() {
        if(reviewInfo != null) {
            Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
            flow.addOnCompleteListener(task -> {
                Toast.makeText(this, "Thank for you rating <3", Toast.LENGTH_SHORT).show();
            });
        }
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