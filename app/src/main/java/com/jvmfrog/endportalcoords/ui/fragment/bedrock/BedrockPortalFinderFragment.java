package com.jvmfrog.endportalcoords.ui.fragment.bedrock;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentBedrockPortalFinderBinding;
import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.ui.Dialogs;
import com.jvmfrog.endportalcoords.util.EndPortalCalculator;
import com.jvmfrog.endportalcoords.util.OtherUtils;
import com.jvmfrog.endportalcoords.util.Point;

public class BedrockPortalFinderFragment extends Fragment {

    private FragmentBedrockPortalFinderBinding binding;
    private String coords;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBedrockPortalFinderBinding.inflate(inflater, container, false);

        binding.firstStepBtn.setOnClickListener(view -> {
            if (!binding.firstXCoord.getText().toString().isEmpty() &&
                    !binding.firstZCoord.getText().toString().isEmpty()) {
                showNextScreen();
                binding.stepView.go(1, true);
            } else {
                System.out.println("Error fields must be filled");
                Dialogs.checkAllFields(view.getContext());
            }
        });

        binding.secondStepBtn.setOnClickListener(view -> {
            if (!binding.secondXCoord.getText().toString().isEmpty() &&
                    !binding.secondZCoord.getText().toString().isEmpty()) {
                showNextScreen();
                binding.stepView.go(2, true);
            } else {
                System.out.println("Error fields must be filled");
                Dialogs.checkAllFields(view.getContext());
            }
        });

        binding.thirdStepBtn.setOnClickListener(view -> {
            if (!binding.thirdXCoord.getText().toString().isEmpty() &&
                    !binding.thirdZCoord.getText().toString().isEmpty()) {
                showNextScreen();
                binding.stepView.go(3, true);
            } else {
                System.out.println("Error fields must be filled");
                Dialogs.checkAllFields(view.getContext());
            }
        });


        binding.fourthStepBtn.setOnClickListener(view -> {
            if (!binding.fourthXCoord.getText().toString().isEmpty() &&
                    !binding.fourthZCoord.getText().toString().isEmpty()) {

                try {
                    float x = Float.parseFloat(binding.firstXCoord.getText().toString()) + .5f;
                    float z = Float.parseFloat(binding.firstZCoord.getText().toString()) + .5f;
                    float x2 = Float.parseFloat(binding.secondXCoord.getText().toString());
                    float z2 = Float.parseFloat(binding.secondZCoord.getText().toString());
                    float x3 = Float.parseFloat(binding.thirdXCoord.getText().toString()) + .5f;
                    float z3 = Float.parseFloat(binding.thirdZCoord.getText().toString()) + .5f;
                    float x4 = Float.parseFloat(binding.fourthXCoord.getText().toString());
                    float z4 = Float.parseFloat(binding.fourthZCoord.getText().toString());

                    Point endPortal = EndPortalCalculator.calculate(
                            new Point(x, z),
                            new Point(x2,z2),
                            new Point(x3,z3),
                            new Point(x4,z4)
                    );

                    binding.portalCoords.setText("X: " + (int) endPortal.x + " " + "Z: " + (int) endPortal.y);
                    coords = (int) endPortal.x + " " + (int) endPortal.y;

                    showNextScreen();
                    binding.stepView.go(4, true);
                    binding.stepView.done(true);

                } catch (AnglesEqualException e) {
                    e.printStackTrace();
                } catch (AnglesOppositeException e) {
                    e.printStackTrace();
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

        binding.copyBtn.setOnClickListener(v -> {
            OtherUtils.copyToClipboard(getContext(), coords);
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
        binding.thirdXCoord.setText("");
        binding.thirdZCoord.setText("");
        binding.fourthXCoord.setText("");
        binding.fourthZCoord.setText("");
    }
}