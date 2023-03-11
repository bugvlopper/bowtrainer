package com.example.boxtrainer.database.trainingdatabase;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class TrainingListAdapter extends ListAdapter<TrainingDB, TrainingViewHolder> {

    public TrainingListAdapter(@NonNull DiffUtil.ItemCallback<TrainingDB> diffCallback) {
        super(diffCallback);
    }
    @Override
    public TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TrainingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(TrainingViewHolder holder, int position) {
        TrainingDB current = getItem(position);
        holder.bind(current.getName(),current.getRoundNumber(),current.getRoundTime(), current.getRestTime());
    }

    public static class TrainingDiff extends DiffUtil.ItemCallback<TrainingDB> {

        @Override
        public boolean areItemsTheSame(@NonNull TrainingDB oldItem, @NonNull TrainingDB newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TrainingDB oldItem,@NonNull TrainingDB newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
