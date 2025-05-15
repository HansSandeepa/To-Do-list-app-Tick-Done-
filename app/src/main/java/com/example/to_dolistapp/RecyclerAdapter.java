package com.example.to_dolistapp;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

        //setup red border for overdue tasks
        setOverdueBorder(holder,id);
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

        public void setTaskItemData(int id, String date, String time, String taskName){
            this.id.setText(String.valueOf(id));
            this.date.setText(date);
            this.time.setText(time);
            this.taskName.setText(taskName);
        }

    }

    /* ---Overdue task list--- */
    private void setOverdueBorder(RecyclerAdapter.ViewHolder holder,int task_id){

        try (TasksDatabase db = new TasksDatabase(holder.itemView.getContext())) {
            String date,time;
//
//            //get today's date and current time
//            today = TimeConversion.currentDate();
//            curTime = TimeConversion.currentTime();
//
            //get overdue time and of event
            Cursor c = db.getTaskTimeAndDate(task_id);
            if (c.moveToFirst()){
                date = c.getString(0);
                time = c.getString(1);

                Log.d("DateDebug",date);
                Log.d("DateDebug",time);

                //setup overdue styles
                if(TimeConversion.isDateTimeInThePast(date,time)){
                    Drawable overdueBorder = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.overdue_task_item_border);
                    holder.itemView.setBackground(overdueBorder);
                    holder.taskName.setTextColor(Color.RED);
                    holder.date.setTextColor(Color.RED);
                    holder.time.setTextColor(Color.RED);
                }

            }
        }
    }


    /* ---event listeners--- */
    //checkbox clicked listener
    private void checkBoxClicked(RecyclerAdapter.ViewHolder holder,int task_id){
        holder.checkBox.setOnCheckedChangeListener(null);   //remove existing listener to prevent multiple listeners on recycler view

        //set new OnCheckedChangeListener for THIS specific item
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            try (TasksDatabase db = new TasksDatabase(holder.itemView.getContext())) {
                db.deleteTask(task_id);
                int curPosition = holder.getAdapterPosition();

                if(curPosition != RecyclerView.NO_POSITION){
                    tasksList.remove(curPosition);
                    notifyItemRemoved(curPosition);
                    notifyItemRangeChanged(curPosition,tasksList.size());

                    Toast.makeText(holder.itemView.getContext() ,"Task Completed!", Toast.LENGTH_LONG).show();
                } else{
                    Log.w("RecyclerAdapter", "Could not get valid adapter position for deletion.");
                    Toast.makeText(holder.itemView.getContext() ,"Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                Toast.makeText(holder.itemView.getContext() ,"Something went wrong!", Toast.LENGTH_LONG).show();
                buttonView.setChecked(false);
            }
        });
    }
}