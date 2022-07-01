package com.jvmfrog.endportalcoords.ui.fragment.java;

import static com.jvmfrog.endportalcoords.util.FragmentUtils.changeFragmentWithRightToLeftAnimation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentJavaSecondStepBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.jvmfrog.endportalcoords.util.EndPortalCalculator;
import com.jvmfrog.endportalcoords.util.Point;
import com.shuhart.stepview.StepView;

public class JavaSecondStepFragment extends Fragment {

    private FragmentJavaSecondStepBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        binding = FragmentJavaSecondStepBinding.inflate(inflater, container, false);

        StepView stepView = getActivity().findViewById(R.id.step_view);
        binding.secondStepBtn.setOnClickListener(
                v -> {
                    if (!binding.secondXCoord.getText().toString().isEmpty() &&
                            !binding.secondZCoord.getText().toString().isEmpty() &&
                            !binding.secondThrowAngle.getText().toString().isEmpty()) {
                        Bundle finalBundle = new Bundle();
                        finalBundle.putAll(getArguments());
                        finalBundle.putFloat("secondX", Float.parseFloat(binding.secondXCoord.getText().toString()));
                        finalBundle.putFloat("secondZ", Float.parseFloat(binding.secondZCoord.getText().toString()));
                        finalBundle.putFloat("secondAngle", Float.parseFloat(binding.secondThrowAngle.getText().toString()));

                        try {
                            Point endPortal = EndPortalCalculator.calculate(
                                    new Point(finalBundle.getFloat("firstX"), finalBundle.getFloat("firstZ")),
                                    new Point(finalBundle.getFloat("secondX"), finalBundle.getFloat("secondZ")),
                                    finalBundle.getFloat("firstAngle"), finalBundle.getFloat("secondAngle"));

                            changeFragmentWithRightToLeftAnimation(getActivity(), new JavaFinishStepFragment(), R.id.wrapper, finalBundle);
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
}