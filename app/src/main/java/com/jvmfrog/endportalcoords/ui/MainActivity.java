package com.jvmfrog.endportalcoords.ui;

import static com.jvmfrog.endportalcoords.util.FragmentUtils.changeFragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.databinding.ActivityMainBinding;
import com.jvmfrog.endportalcoords.ui.fragment.AboutFragment;
import com.jvmfrog.endportalcoords.ui.fragment.bedrock.BedrockPortalFinderFragment;
import com.jvmfrog.endportalcoords.ui.fragment.java.JavaPortalFinderFragment;
import com.jvmfrog.endportalcoords.ui.fragment.GuideFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ConsentInformation consentInformation;
    private AdRequest adRequest;
    private ConsentForm consentForm;

    private boolean isSwitching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changeFragment(this, new JavaPortalFinderFragment(), R.id.frame, null);

        MobileAds.initialize(this, initializationStatus -> {
            //
        });

        adRequest = new AdRequest.Builder()
                .build();

        // Set tag for underage of consent. false means users are not underage.
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setAdMobAppId(getString(R.string.app_id))
                .setTagForUnderAgeOfConsent(false)
                .build();

        // Debug settings for Form
        ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(this)
                .setDebugGeography(ConsentDebugSettings
                        .DebugGeography
                        .DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId("AF0F2B6E3BCDC6ACBFD315C64B00")
                .build();

        ConsentRequestParameters debugParams = new ConsentRequestParameters
                .Builder()
                .setConsentDebugSettings(debugSettings)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                () -> {
                    // The consent information state was updated.
                    // You are now ready to check if a form is available.
                    if (consentInformation.isConsentFormAvailable()) {
                        loadForm();
                    }
                },
                formError -> {
                    // Handle the error.
                }
        );

        binding.calculatorSwitchBtn.setOnClickListener(v -> {
            if (!isSwitching) {
                isSwitching = true;
                changeFragment(this, new BedrockPortalFinderFragment(), R.id.frame, null);
                binding.calculatorSwitchBtn.setText(R.string.switch_to_java);
            } else {
                isSwitching = false;
                changeFragment(this, new JavaPortalFinderFragment(), R.id.frame, null);
                binding.calculatorSwitchBtn.setText(R.string.switch_to_bedrock);
            }
        });

        //Переключатель для нижнего бара
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calculate:
                    changeFragment(this, new JavaPortalFinderFragment(), R.id.frame, null);
                    binding.calculatorSwitchBtn.show();
                    break;
                case R.id.guide:
                    changeFragment(this, new GuideFragment(), R.id.frame, null);
                    binding.calculatorSwitchBtn.hide();
                    break;
                case R.id.about:
                    changeFragment(this, new AboutFragment(), R.id.frame, null);
                    binding.calculatorSwitchBtn.hide();
                    break;
            }
            return true;
        });
    }

    public void loadForm() {
        UserMessagingPlatform.loadConsentForm(
                this,
                consentForm -> {
                    MainActivity.this.consentForm = consentForm;
                    if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.UNKNOWN) {
                        consentForm.show(
                                MainActivity.this,
                                formError -> {
                                    // Handle dismissal by reloading form.
                                    loadForm();
                                }
                        );
                    }
                    if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                        consentForm.show(
                                MainActivity.this,
                                formError -> {
                                    // Handle dismissal by reloading form.
                                    loadForm();
                                }
                        );
                    }
                },
                formError -> {
                    // Handle the error
                }
        );
    }
}