package com.jvmfrog.endportalcoords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.jvmfrog.endportalcoords.R;
import com.jvmfrog.endportalcoords.util.NavigationUtils;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private List<MainModel> list;

    public MainAdapter(Context context, List<MainModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.button_item_for_adapter, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainModel model = list.get(position);
        holder.imageView.setImageResource(model.getImageResource());
        holder.textView.setText(model.getTextResource());

        holder.itemView.setOnClickListener(v -> {
            NavigationUtils.navigateWithNavHost((FragmentActivity) context, R.id.nav_host_fragment, model.getNavigateTo());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private MaterialTextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_container);
            textView = itemView.findViewById(R.id.text_container);
        }
    }
}
