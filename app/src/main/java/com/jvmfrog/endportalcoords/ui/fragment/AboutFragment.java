package com.jvmfrog.endportalcoords.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.BuildConfig;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.util.ChromeCustomTab;
import com.jvmfrog.endportalcoords.databinding.FragmentAboutBinding;
import com.jvmfrog.endportalcoords.util.OtherUtils;

public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);

        //Сам разберешся
        final ChromeCustomTab chromeCustomTabAPI = new ChromeCustomTab(getActivity());

        binding.appVersionBtn.setText(getString(R.string.version) + ": " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");
        binding.appVersionBtn.setOnClickListener(v -> {
            //PlayCore.inAppUpdate(getActivity());
        });
        binding.reviewBtn.setOnClickListener(v -> {
            //PlayCore.inAppReview(getActivity());
        });
        binding.sourceCodeBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.source_code_url), R.color.white));
        binding.kirillBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.kirill_url), R.color.white));
        binding.ibragimBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.ibragim_url), R.color.white));
        binding.mailBtn.setOnClickListener(v -> {OtherUtils.sendEmail(getActivity(), "Test");});
        binding.vkGroupBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.JVMFrog), R.color.white));
        binding.otherAppsBtn.setOnClickListener(v -> {OtherUtils.otherApps(getActivity());});
        binding.translateAppBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.crowdin_project_url), R.color.white));


        return binding.getRoot();
    }
}