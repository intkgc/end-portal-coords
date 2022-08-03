package com.jvmfrog.endportalcoords.ui.fragment.bedrock;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentBedrockPortalFinderBinding;

public class BedrockPortalFinderFragment extends Fragment {

    private FragmentBedrockPortalFinderBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBedrockPortalFinderBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}