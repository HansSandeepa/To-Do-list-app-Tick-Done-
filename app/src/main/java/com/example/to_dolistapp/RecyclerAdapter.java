package com.example.to_dolistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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
        int id = tasksList.get(position).getId();
        String date = tasksList.get(position).getDate();
        String time = tasksList.get(position).getTime();
        String taskName = tasksList.get(position).getTaskName();

        holder.setTaskItemData(id, date, time, taskName);

    //checkbox clicked listener
        checkBoxClicked(holder,id);
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }
 
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView id,date,time,taskName;
        private final CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.item_id);
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

        public void setTaskItemData(int id, String date, String time, String taskName){
            this.id.setText(String.valueOf(id));
            this.date.setText(date);
            this.time.setText(time);
            this.taskName.setText(taskName);
        }

    }

    /* ---event listeners--- */
    //checkbox clicked listener
    void checkBoxClicked(RecyclerAdapter.ViewHolder holder,int task_id){
        holder.checkBox.setOnCheckedChangeListener(null);   //remove existing listener to prevent multiple listeners on recycler view

        //set new OnCheckedChangeListener for THIS specific item
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try (TasksDatabase db = new TasksDatabase(holder.itemView.getContext())) {
                    db.deleteTask(task_id);
                    notifyItemRemoved(holder.getAdapterPosition());
                    Toast.makeText(holder.itemView.getContext() ,"Task Completed!", Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(holder.itemView.getContext() ,"Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}