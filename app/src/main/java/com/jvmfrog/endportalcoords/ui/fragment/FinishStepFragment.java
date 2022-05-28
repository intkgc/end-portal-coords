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
import com.jvmfrog.endportalcoords.databinding.FragmentFinishStepBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.shuhart.stepview.StepView;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class FinishStepFragment extends Fragment {

    private FragmentFinishStepBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFinishStepBinding.inflate(inflater, container, false);
        loadSettings();

        StepView stepView = getActivity().findViewById(R.id.step_view);

        try {
            Point endPortal = EndPortal.getPortalCoords(new Point(Settings.firstXCoordinate, Settings.firstZCoordinate),
                    new Point(Settings.secondXCoordinate, Settings.secondZCoordinate), Settings.firstThrowAngle, Settings.secondThrowAngle);
            System.out.println(endPortal.x + " " + endPortal.z);

            binding.portalCoords.setText("X: " + (int) endPortal.x + " × " + "Z: " + (int) endPortal.z);

            //Dialogs.endPortalCoordinates(this, endPortal.x, endPortal.z).show();
        } catch (AnglesEqualException e) {
            Dialogs.angleEqualException(getContext());
        } catch (AnglesOppositeException e) {
            Dialogs.angleOppositeException(getContext());
        }

        binding.finishStepBtn.setOnClickListener(view -> {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FirstStepFragment fragment = new FirstStepFragment();
            manager.beginTransaction().replace(R.id.wrapper, fragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            stepView.go(0,true);
            stepView.done(false);
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