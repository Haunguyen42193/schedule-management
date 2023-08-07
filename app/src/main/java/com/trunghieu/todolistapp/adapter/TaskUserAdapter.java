package com.trunghieu.todolistapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trunghieu.todolistapp.R;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Task;

import java.util.ArrayList;

public class TaskUserAdapter extends RecyclerView.Adapter<TaskUserAdapter.ViewHolder> {
    private ArrayList<Task> listTask;
    private Context context;
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private OnItemClickListener clickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }
    public TaskUserAdapter(ArrayList<Task> listTask, Context context) {
        this.context = context;
        this.listTask = listTask;
    }

    @NonNull
    @Override
    public TaskUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_user_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSttTaskUser, txtUserTitleItem, txtUserCateItem;
        CheckBox cbIsCompleteTaskUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSttTaskUser = (TextView) itemView.findViewById(R.id.txtSttTaskUser);
            txtUserTitleItem = (TextView) itemView.findViewById(R.id.txtUserTitleItem);
            cbIsCompleteTaskUser = (CheckBox) itemView.findViewById(R.id.cbIsCompleteTaskUser);
            txtUserCateItem = (TextView) itemView.findViewById(R.id.txtUserCateItem);
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dbHelper = new DBHelper(context);
        Task task = listTask.get(position);
        holder.txtSttTaskUser.setText(String.valueOf(position + 1));
        holder.txtUserTitleItem.setText(task.getTitle());
        holder.txtUserCateItem.setText(dbHelper.getCategoryById(task.getCategoryID()).getName());
        if (task.getCompleted() != null) {
            holder.cbIsCompleteTaskUser.setChecked(true);
        }else holder.cbIsCompleteTaskUser.setChecked(false);
        holder.itemView.setOnClickListener(view -> {
            if(clickListener != null) {
                clickListener.onItemClick(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateTaskList(ArrayList<Task> updatedList) {
        listTask.clear();
        listTask.addAll(updatedList);
        notifyDataSetChanged();
    }
}
