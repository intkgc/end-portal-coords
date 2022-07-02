package com.jvmfrog.endportalcoords.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.adapter.Adapter;
import com.jvmfrog.endportalcoords.adapter.Model;
import com.jvmfrog.endportalcoords.databinding.FragmentHistoryBinding;
import com.jvmfrog.endportalcoords.util.SharedPreferenceUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private ArrayList<Model> items_list;
    private Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        SharedPreferenceUtils.loadModelArrayList(getContext(), "coordinates", "data", items_list);

        binding.recview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter((ArrayList<Model>) items_list, getActivity());
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recview);
        binding.recview.setAdapter(adapter);

        if (items_list.isEmpty()) {
            binding.recview.setVisibility(View.GONE);
            binding.linear.setVisibility(View.VISIBLE);
        } else {
            binding.recview.setVisibility(View.VISIBLE);
            binding.linear.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            items_list.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            Toast.makeText(getContext(), R.string.coordinates_removed, Toast.LENGTH_SHORT).show();
            SharedPreferenceUtils.saveModelArrayList(getContext(), "coordinates", "data", items_list);

            if (items_list.isEmpty()) {
                binding.recview.setVisibility(View.GONE);
                binding.linear.setVisibility(View.VISIBLE);
            } else {
                binding.recview.setVisibility(View.VISIBLE);
                binding.linear.setVisibility(View.GONE);
            }
        }
    };
}