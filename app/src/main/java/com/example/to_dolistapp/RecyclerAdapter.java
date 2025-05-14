package com.example.to_dolistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final List<TaskModel> tasksList;

    public RecyclerAdapter(List<TaskModel> tasksList) {
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        String date = tasksList.get(position).getDate();
        String time = tasksList.get(position).getTime();
        String taskName = tasksList.get(position).getTaskName();

        holder.setTaskItemData(date, time, taskName);
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }
 
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView date,time,taskName;
        private final CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.item_date);
            time = itemView.findViewById(R.id.item_time);
            taskName = itemView.findViewById(R.id.item_name);
            checkBox = itemView.findViewById(R.id.item_check);

        }

        public TextView getDate() {
            return date;
        }

        public TextView getTime() {
            return time;
        }

        public TextView getTaskName() {
            return taskName;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setTaskItemData(String date, String time, String taskName){
            this.date.setText(date);
            this.time.setText(time);
            this.taskName.setText(taskName);
        }

    }
}