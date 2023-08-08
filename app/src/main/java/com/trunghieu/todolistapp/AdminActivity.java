package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.User;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtAdminName;
    private Button btnTask;
    private Button btnUser;
    private String userName;
    private User userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        txtAdminName = (TextView) findViewById(R.id.txtAdminName);
        btnTask = (Button) findViewById(R.id.btnTaskActivity);
        btnUser = (Button) findViewById(R.id.btnUserActivity);
        DBHelper dbHelper = new DBHelper(this);
        SharedPreferences preferences = getSharedPreferences("session", MODE_PRIVATE);
        userLogin = dbHelper.getUserById(preferences.getInt("user-id", -1));
        if (userLogin!=null) {
            userName = userLogin.getName();
            // Hiển thị tên người dùng lên TextView
            txtAdminName.setText("Welcome, " + userName + "!");
        }
        btnTask.setOnClickListener(this);
        btnUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(btnTask.getId() == v.getId()) {
            Intent intent = new Intent(this, ListTaskActivity.class);

            startActivity(intent);
        }
        if(btnUser.getId() == v.getId()) {
            Intent intent = new Intent(this, ListUserActivity.class);
            startActivity(intent);
        }
    }
}