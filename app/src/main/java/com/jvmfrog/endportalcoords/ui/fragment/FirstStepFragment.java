package com.jvmfrog.endportalcoords.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.config.Settings;
import com.jvmfrog.endportalcoords.config.SettingsAssist;
import com.jvmfrog.endportalcoords.databinding.FragmentFirstStepBinding;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.shuhart.stepview.StepView;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class FirstStepFragment extends Fragment {

    private FragmentFirstStepBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstStepBinding.inflate(inflater, container, false);

        StepView stepView = getActivity().findViewById(R.id.step_view);

        //Тут ты и так знаешь
        binding.firstStepBtn.setOnClickListener(view -> {
            if (!binding.firstXCoord.getText().toString().isEmpty() &&
                    !binding.firstZCoord.getText().toString().isEmpty() &&
                    !binding.firstThrowAngle.getText().toString().isEmpty()) {

                Settings.firstXCoordinate = Float.parseFloat(binding.firstXCoord.getText().toString());
                Settings.firstZCoordinate = Float.parseFloat(binding.firstZCoord.getText().toString());
                Settings.firstThrowAngle = Float.parseFloat(binding.firstThrowAngle.getText().toString());
                saveSettings();

                replaceFragment(new SecondStepFragment());

                stepView.go(1, true);
            } else {
                System.out.println("Error fields must be filled");
                Dialogs.checkAllFields(view.getContext());
            }
        });

        return binding.getRoot();
    }

    //Лучше не трогать :)
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        fragmentTransaction.replace(R.id.wrapper, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    //мое любимое сохранение
    public void saveSettings() {
        File settingsFile = new File(getContext().getExternalFilesDir(null), "Settings.json");

        try {
            SettingsAssist.save(settingsFile, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //мое любимое загрузка сохранений
    public void loadSettings() {
        File settingsFile = new File(getContext().getExternalFilesDir(null), "Settings.json");

        try {
            SettingsAssist.load(settingsFile, Settings.class);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}