package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.trunghieu.todolistapp.adapter.TaskAdapter;
import com.trunghieu.todolistapp.model.Task;

import java.util.ArrayList;

public class ListTaskActivity extends AppCompatActivity {
    private ArrayList<Task> listTask = new ArrayList<>();
    private TaskAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);

        recyclerView = (RecyclerView) findViewById(R.id.rcvTaskListAdmin);
        adapter = new TaskAdapter(listTask, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}