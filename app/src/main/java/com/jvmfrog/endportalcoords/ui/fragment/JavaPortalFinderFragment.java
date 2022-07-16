package com.jvmfrog.endportalcoords.ui.fragment;

import static com.jvmfrog.endportalcoords.util.FragmentUtils.changeFragmentWithRightToLeftAnimation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentJavaEndPortalFinderBinding;
import com.jvmfrog.endportalcoords.databinding.FragmentJavaPortalFinderBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.jvmfrog.endportalcoords.ui.fragment.java.JavaFinishStepFragment;
import com.jvmfrog.endportalcoords.util.EndPortalCalculator;
import com.jvmfrog.endportalcoords.util.Point;

public class JavaPortalFinderFragment extends Fragment {

    private FragmentJavaPortalFinderBinding binding;
    private String coords, firstX;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJavaPortalFinderBinding.inflate(inflater, container, false);

        binding.firstStepBtn.setOnClickListener(view -> {
            if (!binding.firstXCoord.getText().toString().isEmpty() &&
                    !binding.firstZCoord.getText().toString().isEmpty() &&
                    !binding.firstThrowAngle.getText().toString().isEmpty()) {
                showNextScreen();
                binding.stepView.go(1, true);
            } else {
                System.out.println("Error fields must be filled");
                Dialogs.checkAllFields(view.getContext());
            }
        });


        binding.secondStepBtn.setOnClickListener(view -> {
            if (!binding.secondXCoord.getText().toString().isEmpty() &&
                    !binding.secondZCoord.getText().toString().isEmpty() &&
                    !binding.secondThrowAngle.getText().toString().isEmpty()) {

                try {
                    Point endPortal = EndPortalCalculator.calculate(
                            new Point(Float.parseFloat(binding.firstXCoord.getText().toString()),
                                    Float.parseFloat(binding.firstZCoord.getText().toString())),
                            new Point(Float.parseFloat(binding.secondXCoord.getText().toString()),
                                    Float.parseFloat(binding.secondXCoord.getText().toString())),
                            Float.parseFloat(binding.firstThrowAngle.getText().toString()),
                            Float.parseFloat(binding.secondThrowAngle.getText().toString()));

                    binding.portalCoords.setText("X: " + (int) endPortal.x + " × " + "Z: " + (int) endPortal.y);
                    coords = (int) endPortal.x + " " + (int) endPortal.y;

                    showNextScreen();
                    binding.stepView.go(2, true);
                    binding.stepView.done(true);

                } catch (AnglesEqualException e) {
                    Dialogs.angleEqualException(getContext());
                } catch (AnglesOppositeException e) {
                    Dialogs.angleOppositeException(getContext());
                }
            } else {
                System.out.println("Error fields must be filled");
                Dialogs.checkAllFields(view.getContext());
            }
        });

        binding.finishStepBtn.setOnClickListener(view -> {
            binding.flipper.setDisplayedChild(0);
            binding.stepView.go(0, true);
            binding.stepView.done(false);
            clearTextInput();
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

    private void clearTextInput() {
        binding.firstXCoord.setText("");
        binding.firstZCoord.setText("");
        binding.secondXCoord.setText("");
        binding.secondZCoord.setText("");
        binding.firstThrowAngle.setText("");
        binding.secondThrowAngle.setText("");
    }
}