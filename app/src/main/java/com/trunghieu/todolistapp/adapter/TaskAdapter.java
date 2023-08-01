package com.trunghieu.todolistapp.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trunghieu.todolistapp.R;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Task> listTask;
    private Context context;
    private SQLiteDatabase database;

    public TaskAdapter(ArrayList<Task> listTask, Context context) {
        this.listTask = listTask;
        this.context = context;
        this.database = new DBHelper(context).getReadableDatabase();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = listTask.get(position);
        holder.txtSttTaskAdmin.setText(String.valueOf(position + 1));
        holder.txtTaskItemTitleAdmin.setText(task.getTitle());
        holder.cbIsCompleteTaskAdmin.setChecked(!task.getCompleted().isEmpty());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSttTaskAdmin, txtTaskItemTitleAdmin;
        CheckBox cbIsCompleteTaskAdmin;
        public ViewHolder(View itemView) {
            super(itemView);
            txtSttTaskAdmin = (TextView) itemView.findViewById(R.id.txtSttTaskAdmin);
            txtTaskItemTitleAdmin = (TextView) itemView.findViewById(R.id.txtTaskItemTitleAdmin);
            cbIsCompleteTaskAdmin = (CheckBox) itemView.findViewById(R.id.cbIsCompleteTaskAdmin);
        }
    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }
}
