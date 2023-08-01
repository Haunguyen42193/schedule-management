package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {
    TextView txtUser;
    TextView txtTitle;
    TextView txtDes;
    TextView txtSche;
    TextView txtCreate;
    TextView txtComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
    }
}