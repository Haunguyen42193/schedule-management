package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TaskDetailActivity extends AppCompatActivity {
    private TextView txtId;
    private TextView txtUser;
    private TextView txtTitle;
    private TextView txtDes;
    private TextView txtStart;
    private Switch swComplete;
    private TextView txtCate;
    private Calendar calendarStart;
    private Calendar calendarComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        //lấy các đối tượng giao diện
        txtCate = (TextView) findViewById(R.id.txtCate);
        swComplete = (Switch) findViewById(R.id.swComplete);
        txtStart = (TextView) findViewById(R.id.txtStart);
        txtDes = (TextView) findViewById(R.id.txtDes);
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtId = (TextView) findViewById(R.id.txtId);
        calendarStart = Calendar.getInstance();
        calendarComplete = Calendar.getInstance();

        //Nhận dữ liệu từ ListTaskActivity
        Intent myReceiveIntent = getIntent();
        Bundle bundle = myReceiveIntent.getExtras();
        txtId.setText(bundle.getString("task-id"));
        txtUser.setText(bundle.getString("task-user"));
        txtTitle.setText(bundle.getString("task-title"));
        txtDes.setText(bundle.getString("task-description"));
        txtStart.setText(bundle.getString("task-start"));
        if(!Objects.equals(bundle.getString("task-complete"), "Time not set"))
            swComplete.setChecked(true);
        else
            swComplete.setChecked(false);
        txtCate.setText(bundle.getString("task-cate"));
        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDateTimePicker(TaskDetailActivity.this, calendarStart, txtStart);
            }
        });
        Date startDate;
        try {
            startDate = Utils.sdf.parse(bundle.getString("task-start"));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        calendarStart.setTime(startDate);
    }
}