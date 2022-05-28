package com.jvmfrog.endportalcoords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {

    private List<ViewPagerItem> viewPagerItems;

    public ViewPagerAdapter(List<ViewPagerItem> viewPagerItems) {
        this.viewPagerItems = viewPagerItems;
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPagerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_first_step, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        holder.setViewPagerData(viewPagerItems.get(position));
    }

    @Override
    public int getItemCount() {
        return viewPagerItems.size();
    }

    class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

        void setViewPagerData(ViewPagerItem viewPagerItem) {
            title.setText(viewPagerItem.getTitle());
        }
    }
}
