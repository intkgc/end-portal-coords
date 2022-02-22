package com.jvmfrog.endportalcoords.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jvmfrog.endportalcoords.EndPortal;
import com.jvmfrog.endportalcoords.Point;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout first_x_coord_layout, first_z_coord_layout, first_throw_angle_layout;
    private TextInputLayout second_x_coord_layout, second_z_coord_layout, second_throw_angle_layout;

    private TextInputEditText first_x_coord, first_z_coord, first_throw_angle;
    private TextInputEditText second_x_coord, second_z_coord, second_throw_angle;

    private float first_x, first_z, second_x, second_z, first_ta, second_ta;

    private MaterialButton calculate_coordinates_btn;

    private ExtendedFloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        calculate_coordinates_btn.setOnClickListener(
                v -> {
                    if (!first_x_coord.getText().toString().isEmpty() &&
                            !first_z_coord.getText().toString().isEmpty() &&
                            !first_throw_angle.getText().toString().isEmpty() &&
                            !second_x_coord.getText().toString().isEmpty() &&
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

                            Dialogs.endPortalCoordinates(this, endPortal.x, endPortal.z).show();
                        } catch (AnglesEqualException e) {
                            Dialogs.angleEqualException(v.getContext());
                        } catch (AnglesOppositeException e) {
                            Dialogs.angleOppositeException(v.getContext());
                        }
                    } else {
                        System.out.println("Error fields must be filled");
                        //TODO
                    }
                });

        // Set error text
        //passwordLayout.error = getString(R.string.error)

        // Clear error text
        //passwordLayout.error = null
    }

    public void init() {
        //TextInputLayout
        first_x_coord_layout = findViewById(R.id.first_x_coord_layout);
        first_z_coord_layout = findViewById(R.id.first_z_coord_layout);
        first_throw_angle_layout = findViewById(R.id.first_throw_angle_layout);
        second_x_coord_layout = findViewById(R.id.second_x_coord_layout);
        second_z_coord_layout = findViewById(R.id.second_z_coord_layout);
        second_throw_angle_layout = findViewById(R.id.second_throw_angle_layout);

        //TextInputEditText
        first_x_coord = findViewById(R.id.first_x_coord);
        first_z_coord = findViewById(R.id.first_z_coord);
        first_throw_angle = findViewById(R.id.first_throw_angle);
        second_x_coord = findViewById(R.id.second_x_coord);
        second_z_coord = findViewById(R.id.second_z_coord);
        second_throw_angle = findViewById(R.id.second_throw_angle);

        //Buttons
        calculate_coordinates_btn = findViewById(R.id.calculate_coordinates_btn);
        fab = findViewById(R.id.extended_fab);
    }
}