package com.jvmfrog.endportalcoords.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.EndPortal;
import com.jvmfrog.endportalcoords.Point;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.config.Settings;
import com.jvmfrog.endportalcoords.config.SettingsAssist;
import com.jvmfrog.endportalcoords.databinding.ActivityMain2Binding;
import com.jvmfrog.endportalcoords.databinding.FragmentSecondStepBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.jvmfrog.endportalcoords.ui.MainActivity;
import com.shuhart.stepview.StepView;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class SecondStepFragment extends Fragment {

    private FragmentSecondStepBinding binding;
    private ActivityMain2Binding main2Binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondStepBinding.inflate(inflater, container, false);

        StepView stepView = getActivity().findViewById(R.id.step_view);
        binding.secondStepBtn.setOnClickListener(
                v -> {
                    if (!binding.secondXCoord.getText().toString().isEmpty() &&
                            !binding.secondZCoord.getText().toString().isEmpty() &&
                            !binding.secondThrowAngle.getText().toString().isEmpty()) {

                        Settings.secondXCoordinate = Float.parseFloat(binding.secondXCoord.getText().toString());
                        Settings.secondZCoordinate = Float.parseFloat(binding.secondZCoord.getText().toString());
                        Settings.secondThrowAngle = Float.parseFloat(binding.secondThrowAngle.getText().toString());
                        saveSettings();

                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FinishStepFragment fragment = new FinishStepFragment();
                        manager.beginTransaction().replace(R.id.wrapper, fragment)
                                .addToBackStack(null)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();

                        stepView.go(2, true);
                        stepView.done(true);
                    } else {
                        System.out.println("Error fields must be filled");
                        Dialogs.checkAllFields(v.getContext());
                    }
                }
        );

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