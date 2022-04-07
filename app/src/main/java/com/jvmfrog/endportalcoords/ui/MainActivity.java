package com.jvmfrog.endportalcoords.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.material.appbar.MaterialToolbar;
import com.jvmfrog.endportalcoords.EndPortal;
import com.jvmfrog.endportalcoords.Point;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.ActivityMainBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;

public class MainActivity extends AppCompatActivity implements InAppUpdateManager.InAppUpdateHandler {

    ActivityMainBinding binding;

    private float first_x, first_z, second_x, second_z, first_ta, second_ta;

    private static final int REQ_CODE_VERSION_UPDATE = 99;
    private InAppUpdateManager inAppUpdateManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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

        inAppUpdateManager = InAppUpdateManager.Builder(this, REQ_CODE_VERSION_UPDATE)
                .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
                .mode(Constants.UpdateMode.FLEXIBLE)
                // default is false. If is set to true you,
                // have to manage the user confirmation when
                // you detect the InstallStatus.DOWNLOADED status,
                .useCustomNotification(true)
                .handler(this);

        inAppUpdateManager.checkForAppUpdate();
    }

    @Override
    public void onInAppUpdateError(int code, Throwable error) {
        //
    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {
        //
    }
}