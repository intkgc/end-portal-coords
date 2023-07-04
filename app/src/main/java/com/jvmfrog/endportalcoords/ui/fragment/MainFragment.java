package com.jvmfrog.endportalcoords.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.adapter.MainAdapter;
import com.jvmfrog.endportalcoords.adapter.MainModel;
import com.jvmfrog.endportalcoords.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        try {
            List<MainModel> list = new ArrayList<>();
            list.add(new MainModel(R.drawable.java_edition_logo, R.string.java_calculator, R.id.javaPortalFinderFragment));
            list.add(new MainModel(R.drawable.bedrock_edition_logo, R.string.bedrock_calculator, R.id.bedrockPortalFinderFragment));
            list.add(new MainModel(R.drawable.nether_portal_icon, R.string.nether_calculator, R.id.javaPortalFinderFragment));
            list.add(new MainModel(R.drawable.guide_icon, R.string.guide, R.id.guideFragment));
            list.add(new MainModel(R.drawable.settings_icon, R.string.settings, R.id.javaPortalFinderFragment));
            binding.recview.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recview.setAdapter(new MainAdapter(getActivity(), list));
        } catch (Exception e) {
            Log.e("AHTUNG!", e.toString());
        }

        return binding.getRoot();
    }
}