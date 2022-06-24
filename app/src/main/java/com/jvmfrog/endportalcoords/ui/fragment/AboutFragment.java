package com.jvmfrog.endportalcoords.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.endportalcoords.BuildConfig;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.api.ChromeCustomTabAPI;
import com.jvmfrog.endportalcoords.databinding.FragmentAboutBinding;

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

        final ChromeCustomTabAPI chromeCustomTabAPI = new ChromeCustomTabAPI(getActivity());

        binding.appVersionBtn.setText(getString(R.string.version) + ": " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");
        binding.sourceCodeBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.source_code_url), R.color.white));
        binding.kirillBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.kirill_url), R.color.white));
        binding.ibragimBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.ibragim_url), R.color.white));
        binding.vkGroupBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.JVMFrog), R.color.white));
        binding.otherAppsBtn.setOnClickListener(view1 -> {
            final String appPackageName = getContext().getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:JVMFrog")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.GOOGLE_PLAY))));
            }
        });
        binding.translateAppBtn.setOnClickListener(v -> chromeCustomTabAPI.OpenCustomTab(getActivity(), getString(R.string.crowdin_project_url), R.color.white));


        return binding.getRoot();
    }
}