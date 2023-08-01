package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtAdminName;
    Button btnTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        txtAdminName = (TextView) findViewById(R.id.txtAdminName);
        btnTask = (Button) findViewById(R.id.btnTaskActivity);
        if (getIntent().getExtras() != null) {
            // Lấy Bundle từ Intent
            Bundle bundle = getIntent().getExtras();

            // Kiểm tra xem Bundle có chứa key "UserName" không
            if (bundle.containsKey("UserName")) {
                // Lấy dữ liệu tên người dùng từ Bundle
                String userName = bundle.getString("UserName");

                // Hiển thị tên người dùng lên TextView
                txtAdminName.setText("Welcome, " + userName + "!");
            }
        }
        btnTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(btnTask.getId() == v.getId()) {
            Intent intent = new Intent(this, ListTaskActivity.class);
            startActivity(intent);
        }
    }
}