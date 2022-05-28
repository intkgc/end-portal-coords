package com.jvmfrog.endportalcoords.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.ViewPagerAdapter;
import com.jvmfrog.endportalcoords.ViewPagerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private ViewPagerAdapter adapter;
    private LinearLayout layoutIndicators;
    private MaterialButton buttonAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        layoutIndicators = findViewById(R.id.layoutIndicators);
        buttonAction = findViewById(R.id.viewPagerActionButton);

        setViewPagerItems();
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setAdapter(adapter);
        setupIndicators();
        setCurrentIndicator(0);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        buttonAction.setOnClickListener(view -> {
            if (viewPager2.getCurrentItem() + 1 < adapter.getItemCount()) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            } else {
                //action startActivity(); ...
            }
        });
    }

    private void setViewPagerItems() {
        List<ViewPagerItem> viewPagerItems = new ArrayList<>();

        ViewPagerItem first_step = new ViewPagerItem();
        first_step.setTitle(getString(R.string.enter_firts_xyz_and_throw_angle));

        ViewPagerItem second_step = new ViewPagerItem();
        second_step.setTitle(getString(R.string.enter_second_xyz_and_throw_angle));

        viewPagerItems.add(first_step);
        viewPagerItems.add(second_step);

        adapter = new ViewPagerAdapter(viewPagerItems);
    }

    private void setupIndicators() {
        ImageView[] indicators = new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(getDrawable(R.drawable.viewpager_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int index) {
        int childCount = layoutIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(getDrawable(R.drawable.viewpager_indicator_active));
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.viewpager_indicator_inactive));
            }
        }
        if (index == adapter.getItemCount() - 1) {
            buttonAction.setText(R.string.again);
        } else {
            buttonAction.setText(R.string.next_step);
        }
    }
}