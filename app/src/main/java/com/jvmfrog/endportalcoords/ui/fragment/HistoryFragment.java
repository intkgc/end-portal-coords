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
import com.jvmfrog.endportalcoords.adapter.Adapter;
import com.jvmfrog.endportalcoords.adapter.Model;
import com.jvmfrog.endportalcoords.databinding.FragmentHistoryBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    ArrayList<Model> items_list;
    Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        loadData();

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
            Toast.makeText(getContext(), "Coordinates Removed", Toast.LENGTH_SHORT).show();
            saveData();

            if (items_list.isEmpty()) {
                binding.recview.setVisibility(View.GONE);
                binding.linear.setVisibility(View.VISIBLE);
            } else {
                binding.recview.setVisibility(View.VISIBLE);
                binding.linear.setVisibility(View.GONE);
            }
        }
    };

    //типо сохранение координат
    public void saveData(){
        SharedPreferences prefs = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items_list);
        editor.putString("data", json);
        editor.apply();
    }

    //типо загрузка координат
    public void loadData() {
        SharedPreferences prefs = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("data", null);
        Type type = new TypeToken<ArrayList<Model>>() {}.getType();
        items_list = gson.fromJson(json, type);

        if (items_list == null) {
            items_list = new ArrayList<>();
        }
    }
}