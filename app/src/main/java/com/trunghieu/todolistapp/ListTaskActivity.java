package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.trunghieu.todolistapp.adapter.TaskAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Task;

import java.util.ArrayList;

public class ListTaskActivity extends AppCompatActivity {
    private ArrayList<Task> listTask = new ArrayList<>();
    private TaskAdapter adapter;
    private RecyclerView recyclerView;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        dbHelper = new DBHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.rcvTaskListAdmin);
        listTask = dbHelper.getTaskData();
        for (Task task : listTask) {
            Log.d("ListTaskActivity", "Task ID: " + task.getId());
            Log.d("ListTaskActivity", "Task Title: " + task.getTitle());
            Log.d("ListTaskActivity", "Task Description: " + task.getDescription());
            Log.d("ListTaskActivity", "Task Created: " + task.getCreateDate());
            Log.d("ListTaskActivity", "Task Completed: " + task.getCompleted());
            Log.d("ListTaskActivity", "Task User ID: " + task.getUserId());
            Log.d("ListTaskActivity", "Task Category ID: " + task.getCategoryID());
        }


        adapter = new TaskAdapter(listTask, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}