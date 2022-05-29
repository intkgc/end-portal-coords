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

                        try {
                            Point endPortal = EndPortal.getPortalCoords(new Point(Settings.firstXCoordinate, Settings.firstZCoordinate),
                                    new Point(Settings.secondXCoordinate, Settings.secondZCoordinate), Settings.firstThrowAngle, Settings.secondThrowAngle);

                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FinishStepFragment fragment = new FinishStepFragment();
                            manager.beginTransaction()
                                    .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                            R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                                    .replace(R.id.wrapper, fragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit();

                            stepView.go(2, true);
                            stepView.done(true);
                        } catch (AnglesEqualException e) {
                            Dialogs.angleEqualException(getContext());
                        } catch (AnglesOppositeException e) {
                            Dialogs.angleOppositeException(getContext());
                        }
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