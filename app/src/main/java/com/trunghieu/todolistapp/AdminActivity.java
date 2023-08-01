package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {
    TextView txtAdminName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        String userName = bundle.getString("UserName");
        txtAdminName = findViewById(R.id.txtAdminName);
//        txtAdminName.setText(userName);
    }
}