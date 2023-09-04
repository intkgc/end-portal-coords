package com.jvmfrog.endportalcoords.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentNetherCoordsBinding;
import com.jvmfrog.endportalcoords.util.NetherCoordsCalculator;

public class NetherCoordsFragment extends Fragment {
    private FragmentNetherCoordsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNetherCoordsBinding.inflate(inflater, container, false);

        binding.overworldXCoord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double val = Double.parseDouble(s.toString());
                if (binding.overworldXCoord.isFocused())
                    binding.netherXCoord.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.netherXCoord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.netherXCoord.isFocused()) {
                    try {
                        double inputDouble = Double.parseDouble(s.toString());
                        double result = inputDouble / 8;
                        binding.overworldXCoord.setText(String.valueOf(result));
                    } catch (NumberFormatException e) {
                        Log.d("ПОЙМАЛ ГАДА", e.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return binding.getRoot();
    }
}