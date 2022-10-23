package com.jvmfrog.endportalcoords.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.FragmentGuideDescBinding;

import org.xmlpull.v1.XmlPullParser;

public class GuideDescFragment extends Fragment {

    private FragmentGuideDescBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGuideDescBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}