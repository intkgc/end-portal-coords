package com.jvmfrog.endportalcoords.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.config.Settings;
import com.jvmfrog.endportalcoords.config.SettingsAssist;
import com.jvmfrog.endportalcoords.databinding.ActivityMain2Binding;
import com.jvmfrog.endportalcoords.databinding.FragmentFirstStepBinding;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.jvmfrog.endportalcoords.ui.MainActivity;
import com.shuhart.stepview.StepView;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class FirstStepFragment extends Fragment {

    private FragmentFirstStepBinding binding;
    private ActivityMain2Binding main2Binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstStepBinding.inflate(inflater, container, false);

        StepView stepView = getActivity().findViewById(R.id.step_view);

        binding.firstStepBtn.setOnClickListener(view -> {
            if (!binding.firstXCoord.getText().toString().isEmpty() &&
                    !binding.firstZCoord.getText().toString().isEmpty() &&
                    !binding.firstThrowAngle.getText().toString().isEmpty()) {

                Settings.firstXCoordinate = Float.parseFloat(binding.firstXCoord.getText().toString());
                Settings.firstZCoordinate = Float.parseFloat(binding.firstZCoord.getText().toString());
                Settings.firstThrowAngle = Float.parseFloat(binding.firstThrowAngle.getText().toString());
                saveSettings();

                FragmentManager manager = getActivity().getSupportFragmentManager();
                SecondStepFragment fragment = new SecondStepFragment();
                manager.beginTransaction().replace(R.id.wrapper, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();

                stepView.go(1, true);
            } else {
                System.out.println("Error fields must be filled");
                Dialogs.checkAllFields(view.getContext());
            }
        });

        return binding.getRoot();
    }

    public void saveSettings() {
        File settingsFile = new File(getContext().getFilesDir(), "Settings.json");

        try {
            SettingsAssist.save(settingsFile, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        File settingsFile = new File(getContext().getFilesDir(), "Settings.json");

        try {
            SettingsAssist.load(settingsFile, Settings.class);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}