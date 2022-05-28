package com.jvmfrog.endportalcoords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.ActivityMain2Binding;
import com.jvmfrog.endportalcoords.ui.fragment.FirstStepFragment;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new FirstStepFragment()).commit();

        binding.extendedFab.setOnClickListener(
                v -> {
                    Intent intent = new Intent(MainActivity2.this, GuideActivity.class);
                    startActivity(intent);
                }
        );

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.about) {
                Intent intent = new Intent(MainActivity2.this, AboutAppActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}