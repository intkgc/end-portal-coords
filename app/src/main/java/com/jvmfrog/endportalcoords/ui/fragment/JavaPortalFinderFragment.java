package com.jvmfrog.endportalcoords.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentJavaEndPortalFinderBinding;
import com.jvmfrog.endportalcoords.databinding.FragmentJavaPortalFinderBinding;

public class JavaPortalFinderFragment extends Fragment {

    private FragmentJavaPortalFinderBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJavaPortalFinderBinding.inflate(inflater, container, false);

        binding.firstStepBtn.setOnClickListener(view -> {
            showNextScreen();
            binding.stepView.go(1, true);
        });

        binding.secondStepBtn.setOnClickListener(view -> {
            showNextScreen();
            binding.stepView.go(2, true);
            binding.stepView.done(true);
        });

        binding.finishStepBtn.setOnClickListener(view -> {
            binding.flipper.setDisplayedChild(0);
            binding.stepView.go(0, true);
            binding.stepView.done(false);
        });

        return binding.getRoot();
    }

    // перейти на предыдущий экран с анимацией справа налево
    private void showPreviousScreen() {
        // переход влево доступен только если мы не на первом экране
        if (!isFirst()) {
            binding.flipper.setInAnimation(getContext(), R.anim.enter_right_to_left);
            binding.flipper.setOutAnimation(getContext(), R.anim.exit_right_to_left);
            binding.flipper.showPrevious();
        }
    }

    // определяем, является ли текущий экран первым
    private boolean isFirst() {
        return binding.flipper.getDisplayedChild() == 0;
    }

    // перейти на следующий экран с анимацией слева на право
    private void showNextScreen() {
        // переход вправо доступен только если мы не на последнем экране
        if (!isLast()) {
            binding.flipper.setInAnimation(getContext(), R.anim.enter_right_to_left);
            binding.flipper.setOutAnimation(getContext(), R.anim.exit_right_to_left);
            // переход вправо доступен
            binding.flipper.showNext();
        }
    }

    // определяем, является ли текущий экран последним
    private boolean isLast() {
        return binding.flipper.getDisplayedChild() + 1 == binding.flipper.getChildCount();
    }
}