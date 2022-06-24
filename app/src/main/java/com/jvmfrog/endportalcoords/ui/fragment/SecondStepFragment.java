package com.jvmfrog.endportalcoords.ui.fragment;

import static com.jvmfrog.endportalcoords.util.FragmentUtils.changeFragmentWithAnimation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentSecondStepBinding;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.shuhart.stepview.StepView;

public class SecondStepFragment extends Fragment {

    private FragmentSecondStepBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        binding = FragmentSecondStepBinding.inflate(inflater, container, false);

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
                        changeFragmentWithAnimation(getActivity(), new FinishStepFragment(), R.id.wrapper, finalBundle);

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
}