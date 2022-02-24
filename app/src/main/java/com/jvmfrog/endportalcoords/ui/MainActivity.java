package com.jvmfrog.endportalcoords.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jvmfrog.endportalcoords.EndPortal;
import com.jvmfrog.endportalcoords.Point;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Collections;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;

public class MainActivity extends AppCompatActivity implements InAppUpdateManager.InAppUpdateHandler {

    private MaterialToolbar toolbar;

    private TextInputEditText first_x_coord, first_z_coord, first_throw_angle;
    private TextInputEditText second_x_coord, second_z_coord, second_throw_angle;

    private float first_x, first_z, second_x, second_z, first_ta, second_ta;

    private MaterialButton first_step_btn, second_step_btn, finish_step_btn;
    private TextView portal_coords;

    private ExtendedFloatingActionButton fab;

    private MaterialCardView first_step, next_step, finish_step;

    private StepView stepView;

    private static final int REQ_CODE_VERSION_UPDATE = 99;
    private InAppUpdateManager inAppUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

        init();

        first_step_btn.setOnClickListener(
                v -> {
                    if (!first_x_coord.getText().toString().isEmpty() &&
                            !first_z_coord.getText().toString().isEmpty() &&
                            !first_throw_angle.getText().toString().isEmpty()) {

                        first_step.setVisibility(View.GONE);
                        next_step.setVisibility(View.VISIBLE);
                        finish_step.setVisibility(View.GONE);
                        stepView.go(1, true);

                    } else {
                        System.out.println("Error fields must be filled");
                    }
                });

        second_step_btn.setOnClickListener(
                v -> {
                    if (!second_x_coord.getText().toString().isEmpty() &&
                            !second_z_coord.getText().toString().isEmpty() &&
                            !second_throw_angle.getText().toString().isEmpty()) {

                        first_x = Float.parseFloat(first_x_coord.getText().toString());
                        first_z = Float.parseFloat(first_z_coord.getText().toString());
                        first_ta = Float.parseFloat(first_throw_angle.getText().toString());

                        second_x = Float.parseFloat(second_x_coord.getText().toString());
                        second_z = Float.parseFloat(second_z_coord.getText().toString());
                        second_ta = Float.parseFloat(second_throw_angle.getText().toString());

                        try {
                            Point endPortal = EndPortal.getPortalCoords(new Point(first_x, first_z), new Point(second_x, second_z), first_ta, second_ta);
                            System.out.println(endPortal.x + " " + endPortal.z);

                            portal_coords.setText("X: " + (int) endPortal.x + " × " + "Z: " + (int) endPortal.z);

                            finish_step.setVisibility(View.GONE);
                            next_step.setVisibility(View.GONE);
                            finish_step.setVisibility(View.VISIBLE);
                            stepView.go(2, true);
                            stepView.done(true);

                            //Dialogs.endPortalCoordinates(this, endPortal.x, endPortal.z).show();
                        } catch (AnglesEqualException e) {
                            Dialogs.angleEqualException(MainActivity.this);
                        } catch (AnglesOppositeException e) {
                            Dialogs.angleOppositeException(MainActivity.this);
                        }
                    } else {
                        System.out.println("Error fields must be filled");
                    }
                }
        );

        finish_step_btn.setOnClickListener(
                v -> {
                    first_x_coord.setText("");
                    first_z_coord.setText("");
                    first_throw_angle.setText("");
                    second_x_coord.setText("");
                    second_z_coord.setText("");
                    second_throw_angle.setText("");

                    first_step.setVisibility(View.VISIBLE);
                    next_step.setVisibility(View.GONE);
                    finish_step.setVisibility(View.GONE);
                    stepView.go(0,true);
                    stepView.done(false);
                }
        );

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about:
                        Intent intent = new Intent(MainActivity.this, AboutAppActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        inAppUpdateManager = InAppUpdateManager.Builder(this, REQ_CODE_VERSION_UPDATE)
                .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
                .mode(Constants.UpdateMode.IMMEDIATE)
                // default is false. If is set to true you,
                // have to manage the user confirmation when
                // you detect the InstallStatus.DOWNLOADED status,
                .useCustomNotification(true)
                .handler(this);

        inAppUpdateManager.checkForAppUpdate();
    }

    public void init() {
        first_step = findViewById(R.id.first_step);
        next_step = findViewById(R.id.next_step);
        finish_step = findViewById(R.id.finish_step);

        //TextInputEditText
        first_x_coord = findViewById(R.id.first_x_coord);
        first_z_coord = findViewById(R.id.first_z_coord);
        first_throw_angle = findViewById(R.id.first_throw_angle);
        second_x_coord = findViewById(R.id.second_x_coord);
        second_z_coord = findViewById(R.id.second_z_coord);
        second_throw_angle = findViewById(R.id.second_throw_angle);

        //Buttons
        first_step_btn = findViewById(R.id.first_step_btn);
        second_step_btn = findViewById(R.id.second_step_btn);
        finish_step_btn = findViewById(R.id.finish_step_btn);
        //fab = findViewById(R.id.extended_fab);

        portal_coords = findViewById(R.id.portal_coords);
        toolbar = findViewById(R.id.toolbar);
        stepView = findViewById(R.id.step_view);
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