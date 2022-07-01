package com.jvmfrog.endportalcoords.ui.fragment.java;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentJavaEndPortalFinderBinding;

public class JavaEndPortalFinderFragment extends Fragment {

    private FragmentJavaEndPortalFinderBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJavaEndPortalFinderBinding.inflate(inflater, container, false);

        replaceFragment(new JavaFirstStepFragment());

        return binding.getRoot();
    }

    //Лучше не трогать :)
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.wrapper, fragment);
        fragmentTransaction.commit();
    }

}