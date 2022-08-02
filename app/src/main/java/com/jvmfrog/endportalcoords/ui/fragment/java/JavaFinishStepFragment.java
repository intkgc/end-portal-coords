package com.jvmfrog.endportalcoords.ui.fragment.java;

import static com.jvmfrog.endportalcoords.util.FragmentUtils.changeFragmentWithLeftToRightAnimation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentJavaFinishStepBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.jvmfrog.endportalcoords.util.EndPortalCalculator;
import com.jvmfrog.endportalcoords.util.Point;
import com.shuhart.stepview.StepView;

public class JavaFinishStepFragment extends Fragment {

    private FragmentJavaFinishStepBinding binding;
    private String coords;

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

        return binding.getRoot();
    }
}