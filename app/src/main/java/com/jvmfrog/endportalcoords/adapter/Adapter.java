package com.jvmfrog.endportalcoords.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.ui.fragment.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Model> items_list;
    public Adapter (ArrayList<Model> items_list, Context context) {
        this.items_list = items_list;
        this.context = context;
    };

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        String item_name = items_list.get(position).getItem_name();
        String item_coordinates = items_list.get(position).getItem_coordinates();

        holder.setData(item_name, item_coordinates);
    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView tv_item_position;
        private MaterialTextView tv_item_name;
        private MaterialTextView tv_item_coordinates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_position = itemView.findViewById(R.id.item_position);
            tv_item_name = itemView.findViewById(R.id.item_name);
            tv_item_coordinates = itemView.findViewById(R.id.item_coordinates);

            itemView.setOnClickListener(view -> {
                items_list.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                saveData();
            });
        }

        public void setData(String item_name, String item_coordinates) {
            tv_item_name.setText(item_name);
            tv_item_coordinates.setText(item_coordinates);
        }
    }

    //типо сохранение координат
    public void saveData() {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items_list);
        editor.putString("data", json);
        editor.apply();
    }
}
