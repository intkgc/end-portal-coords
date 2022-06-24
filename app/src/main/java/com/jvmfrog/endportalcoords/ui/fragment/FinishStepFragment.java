package com.jvmfrog.endportalcoords.ui.fragment;

import static com.jvmfrog.endportalcoords.util.FragmentUtils.changeFragmentWithAnimation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.adapter.Adapter;
import com.jvmfrog.endportalcoords.adapter.Model;
import com.jvmfrog.endportalcoords.databinding.FragmentFinishStepBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.jvmfrog.endportalcoords.util.EndPortalCalculator;
import com.jvmfrog.endportalcoords.util.Point;
import com.shuhart.stepview.StepView;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
                             Bundle bundle) {
        binding = FragmentFinishStepBinding.inflate(inflater, container, false);

        StepView stepView = getActivity().findViewById(R.id.step_view);

        items_list = new ArrayList<>();

        try {
            Bundle finalBundle = getArguments();
            Point endPortal = EndPortalCalculator.calculate(
                    new Point(finalBundle.getFloat("firstX"), finalBundle.getFloat("firstZ")),
                    new Point(finalBundle.getFloat("secondX"), finalBundle.getFloat("secondZ")),
                    finalBundle.getFloat("firstAngle"), finalBundle.getFloat("secondAngle"));
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
            changeFragmentWithAnimation(getActivity(), new FirstStepFragment(), R.id.wrapper, bundle);
            stepView.go(0, true);
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

    //типо сохранение координат
    public void saveData() {
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