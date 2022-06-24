package com.jvmfrog.endportalcoords.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jvmfrog.endportalcoords.EndPortal;
import com.jvmfrog.endportalcoords.Point;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.adapter.Adapter;
import com.jvmfrog.endportalcoords.adapter.Model;
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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FinishStepFragment extends Fragment {

    private FragmentFinishStepBinding binding;
    private String coords;
    private Adapter adapter;
    ArrayList<Model> items_list;

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

        items_list = new ArrayList<>();

        try {
            Point endPortal = EndPortal.getPortalCoords(new Point(Settings.firstXCoordinate, Settings.firstZCoordinate),
                    new Point(Settings.secondXCoordinate, Settings.secondZCoordinate), Settings.firstThrowAngle, Settings.secondThrowAngle);
            System.out.println(endPortal.x + " " + endPortal.z);

            binding.portalCoords.setText("X: " + (int) endPortal.x + " × " + "Z: " + (int) endPortal.z);
            coords = "X: " + (int) endPortal.x + " × " + "Z: " + (int) endPortal.z;

        } catch (AnglesEqualException e) {
            Dialogs.angleEqualException(getContext());
        } catch (AnglesOppositeException e) {
            Dialogs.angleOppositeException(getContext());
        }

        //При клике возвращяет пользователя в первый шаг
        //Типо сохроняет коорды и сбрасывает счетчик шагов
        binding.finishStepBtn.setOnClickListener(view -> {
            replaceFragment(new FirstStepFragment());
            stepView.go(0,true);
            stepView.done(false);
        });

        binding.coordsSaveBtn.setOnClickListener(view -> {
            final EditText item_name_edit_text = new EditText(view.getContext());
            AlertDialog builder = new MaterialAlertDialogBuilder(view.getContext())
                    .setIcon(R.drawable.ic_round_save_24)
                    .setTitle(R.string.saving_coordinates)
                    .setMessage(R.string.enter_a_name_for_coordinates)
                    .setView(item_name_edit_text)
                    .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String item_name = String.valueOf(item_name_edit_text.getText());
                            items_list.add(new Model(item_name, coords));
                            saveData();
                        }
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .create();
            builder.show();
        });

        return binding.getRoot();
    }

    //Лучше не трогать :)
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                R.anim.enter_right_to_left, R.anim.exit_right_to_left);
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

    //типо сохранение координат
    public void saveData(){
        SharedPreferences prefs = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items_list);
        editor.putString("data", json);
        editor.apply();
    }

    //типо загрузка координат
    public void loadData() {
        SharedPreferences prefs = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("data", null);
        Type type = new TypeToken<ArrayList<Model>>() {
        }.getType();
        items_list = gson.fromJson(json, type);

        //только дурак не поймет что оно делает
        if (items_list == null) {
            items_list = new ArrayList<>();
        }
    }
}