package com.jvmfrog.endportalcoords.ui.fragment.java;

import static com.jvmfrog.endportalcoords.util.FragmentUtils.changeFragmentWithLeftToRightAnimation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.adapter.Model;
import com.jvmfrog.endportalcoords.databinding.FragmentJavaFinishStepBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.jvmfrog.endportalcoords.util.EndPortalCalculator;
import com.jvmfrog.endportalcoords.util.Point;
import com.jvmfrog.endportalcoords.util.SharedPreferenceUtils;
import com.shuhart.stepview.StepView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JavaFinishStepFragment extends Fragment {

    private FragmentJavaFinishStepBinding binding;
    private String coords;
    private ArrayList<Model> items_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        binding = FragmentJavaFinishStepBinding.inflate(inflater, container, false);

        StepView stepView = getActivity().findViewById(R.id.step_view);

        try {
            Bundle finalBundle = getArguments();
            Point endPortal = EndPortalCalculator.calculate(
                    new Point(finalBundle.getFloat("firstX"), finalBundle.getFloat("firstZ")),
                    new Point(finalBundle.getFloat("secondX"), finalBundle.getFloat("secondZ")),
                    finalBundle.getFloat("firstAngle"), finalBundle.getFloat("secondAngle"));
            System.out.println(endPortal.x + " " + endPortal.y);

            binding.portalCoords.setText("X: " + (int) endPortal.x + " × " + "Z: " + (int) endPortal.y);
            coords = (int) endPortal.x + " " + (int) endPortal.y;

        } catch (AnglesEqualException e) {
            Dialogs.angleEqualException(getContext());
        } catch (AnglesOppositeException e) {
            Dialogs.angleOppositeException(getContext());
        }

        //При клике возвращяет пользователя в первый шаг
        //Типо сохроняет коорды и сбрасывает счетчик шагов
        binding.finishStepBtn.setOnClickListener(view -> {
            changeFragmentWithLeftToRightAnimation(getActivity(), new JavaFirstStepFragment(), R.id.wrapper, bundle);
            stepView.go(0, true);
            stepView.done(false);
        });

        binding.coordsSaveBtn.setOnClickListener(view -> {
            final EditText item_name_edit_text = new EditText(view.getContext());
            new MaterialAlertDialogBuilder(view.getContext())
                    .setIcon(R.drawable.ic_round_save_24)
                    .setTitle(R.string.saving_coordinates)
                    .setMessage(R.string.enter_a_name_for_coordinates)
                    .setView(item_name_edit_text)
                    .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String item_name = String.valueOf(item_name_edit_text.getText());
                            items_list.add(new Model(item_name, getString(R.string.coordinates) + ": " +  coords));
                            SharedPreferenceUtils.saveModelArrayList(getActivity(), "coordinates", "data", items_list);
                        }
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .create()
                    .show();
        });

        return binding.getRoot();
    }
}