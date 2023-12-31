package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    private Button btnTask,btnCategory, btnAudio;
    private Button btnUser;
    private String userName;
    private User userLogin;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        txtAdminName = (TextView) findViewById(R.id.txtAdminName);
        btnTask = (Button) findViewById(R.id.btnTaskActivity);
        btnCategory = (Button) findViewById(R.id.btnCategoryActivity);
        btnUser = (Button) findViewById(R.id.btnUserActivity);
        btnAudio = (Button) findViewById(R.id.btnNotificationActivity);
        getName(txtAdminName);
        btnTask.setOnClickListener(this);
        btnUser = (Button) findViewById(R.id.btnUserActivity);
        DBHelper dbHelper = new DBHelper(this);
        SharedPreferences preferences = getSharedPreferences("session", MODE_PRIVATE);
        userLogin = dbHelper.getUserById(preferences.getInt("user-id", -1));
        if (userLogin != null) {
            userName = userLogin.getName();
            // Hiển thị tên người dùng lên TextView
            txtAdminName.setText("Welcome, " + userName + "!");
        }
        btnTask.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack8);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ListUserActivity.class);
                startActivity(intent);
            }
        });
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ListCategoryActivity.class);
                startActivity(intent);
            }
        });
        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ListAudioActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(btnTask.getId() == v.getId()) {
            Intent intent = new Intent(this, ListTaskActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("user-id", userLogin.getId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if(btnUser.getId() == v.getId()) {
            Intent intent = new Intent(this, ListUserActivity.class);
            startActivity(intent);
        }
    }
    public void getName(TextView v){
        if (getIntent().getExtras() != null) {
            // Lấy Bundle từ Intent
            Bundle bundle = getIntent().getExtras();
            // Kiểm tra xem Bundle có chứa key "UserName" không
            if (bundle.containsKey("UserName")) {
                // Lấy dữ liệu tên người dùng từ Bundle
                String userName = bundle.getString("UserName");
                // Hiển thị tên người dùng lên TextView
                v.setText("Welcome, " + userName + "!");
            }
        }
    }
}