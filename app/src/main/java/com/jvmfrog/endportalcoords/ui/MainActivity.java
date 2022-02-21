package com.jvmfrog.endportalcoords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jvmfrog.endportalcoords.ErrorDialogs;
import com.jvmfrog.endportalcoords.R;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout first_x_coord_layout, first_z_coord_layout, first_throw_angle_layout;
    private TextInputLayout second_x_coord_layout, second_z_coord_layout, second_throw_angle_layout;

    private TextInputEditText first_x_coord, first_z_coord, first_throw_angle;
    private TextInputEditText second_x_coord, second_z_coord, second_throw_angle;

    private MaterialButton calculate_coordinates_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ErrorDialogs errorDialogs = new ErrorDialogs();

        init();

        calculate_coordinates_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorDialogs.angleOppositeException(v.getContext());
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
    }
}