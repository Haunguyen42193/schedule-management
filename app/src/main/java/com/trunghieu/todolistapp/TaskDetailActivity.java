package com.trunghieu.todolistapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Audio;
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.Task;
import com.trunghieu.todolistapp.model.User;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TaskDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtId;
    private TextView txtUser;
    private TextView txtTitle;
    private TextView txtDes;
    private TextView txtStart;
    private Switch swComplete;
    private Spinner spnCateDetail, spnListAudio;
    private Calendar calendarStart;
    private Calendar calendarComplete;
    private Button btnReAddTask;
    private Button btnUpdateTask;
    private Button btnDeleteTask;
    private String[] items;
    private ArrayList<Category> listCate;
    private ArrayList<Audio> listAudio;
    private DBHelper dbHelper;
    private String taskCate;
    private Audio taskAudio;
    private Category selectedCate;
    private Audio selectedAudio;
    private User userLogin;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        //lấy các đối tượng giao diện
        spnCateDetail = (Spinner) findViewById(R.id.spnCateDetail);
        swComplete = (Switch) findViewById(R.id.swComplete);
        txtStart = (TextView) findViewById(R.id.txtStart);
        txtDes = (TextView) findViewById(R.id.txtDes);
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtId = (TextView) findViewById(R.id.txtId);
        calendarStart = Calendar.getInstance();
        calendarComplete = Calendar.getInstance();
        btnReAddTask = (Button) findViewById(R.id.btnReAddTask);
        btnUpdateTask= (Button) findViewById(R.id.btnUpdateTask);
        btnDeleteTask = (Button) findViewById(R.id.btnDeleteTask);
        spnListAudio = (Spinner) findViewById(R.id.spnAudioDetail);

        dbHelper = new DBHelper(this);
        listCate = dbHelper.getCategoriesData();
        listAudio = dbHelper.getAudioData();
        SharedPreferences preferences = getSharedPreferences("session", MODE_PRIVATE);
        userLogin = dbHelper.getUserById(preferences.getInt("user-id", -1));

        btnBack = findViewById(R.id.btnBack4);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "Back";
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                bundle.putString("status",status);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        //Nhận dữ liệu từ ListTaskActivity
        Intent myReceiveIntent = getIntent();
        Bundle bundle = myReceiveIntent.getExtras();
        txtId.setText(bundle.getString("task-id"));
        txtUser.setText(bundle.getString("task-user"));
        txtTitle.setText(bundle.getString("task-title"));
        txtDes.setText(bundle.getString("task-description"));
        txtStart.setText(bundle.getString("task-start"));
        taskCate = bundle.getString("task-cate");
        taskAudio = dbHelper.getAudioByID(bundle.getString("task-audio-id"));

        //Thiết lập giá trị cho spinner category
        if(!listCate.isEmpty()) {
            items = new String[listCate.size()];
            int i = 0;
            int position = 0;
            for(Category cate: listCate) {
                if(Objects.equals(taskCate, cate.getName()))
                    position = i;
                items[i++] = cate.getName();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCateDetail.setAdapter(arrayAdapter);
            spnCateDetail.setSelection(position);
            setUpCateDetail();
        }

        //Thiết lập giá trị cho spinner audio
        if(!listAudio.isEmpty()) {
            items = new String[listAudio.size()];
            int i = 0;
            int position = 0;
            for(Audio audio: listAudio) {
                if(Objects.equals(taskAudio.getId(), audio.getId()))
                    position = i;
                items[i++] = audio.getName();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnListAudio.setAdapter(arrayAdapter);
            spnListAudio.setSelection(position);
            setUpAudioDetail();
        }

        //kiểm tra coi task đã được hoàn thành hay chưa
        if(!Objects.equals(bundle.getString("task-complete"), "Time not set"))
            swComplete.setChecked(true);
        else
            swComplete.setChecked(false);

        //Thiết lập hiển thị lịch cho người dùng chọn
        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDateTimePicker(TaskDetailActivity.this, calendarStart, txtStart);
            }
        });

        //Gán giá trị thiết lập sẵn cho lịch
        Date startDate;
        try {
            startDate = Utils.sdf.parse(bundle.getString("task-start"));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        calendarStart.setTime(startDate);

        btnReAddTask.setOnClickListener(this);
        btnUpdateTask.setOnClickListener(this);
        btnDeleteTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(btnReAddTask.getId() == v.getId()) {
            Intent intent;
            intent = new Intent(this, ReAddTaskActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("re-add-task-title", txtTitle.getText().toString());
            bundle.putString("re-add-task-description", txtDes.getText().toString());
            bundle.putString("re-add-task-start",txtStart.getText().toString());
            bundle.putString("re-add-task-cate", selectedCate.getName());
            bundle.putString("re-add-task-audio", selectedAudio.getId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if (btnUpdateTask.getId() == v.getId()) {
            String status = updateTask();
            Toast.makeText(this, status, Toast.LENGTH_LONG).show();

        }
        if (btnDeleteTask.getId() == v.getId()) {
            showConfirmDialog();
        }
    }

    //Lấy giá trị được chọn trong spinner category
    private void setUpCateDetail () {
        spnCateDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCate = listCate.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpAudioDetail () {
        spnListAudio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAudio = listAudio.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Xử lý update dữ task
    public String updateTask() {
        String title = txtTitle.getText().toString();
        String description = txtDes.getText().toString();
        String startDate = txtStart.getText().toString();
        String cateId = selectedCate.getId();
        String id = txtId.getText().toString();
        String audioId = selectedAudio.getId();
        Task t = dbHelper.getTaskById(id);
        int userId = t.getUserId();
        String complete = null;
        if (TextUtils.isEmpty(title)) {
            txtTitle.setBackgroundResource(R.drawable.border_red);
            return "Input title, please!";
        } else {
            txtTitle.setBackgroundResource(R.drawable.border);
        }

        if (TextUtils.isEmpty(startDate)) {
            txtStart.setBackgroundResource(R.drawable.border_red);
            return "Chose start-time, please!";
        } else
            try {
                txtStart.setBackgroundResource(R.drawable.border);
                Date date = Utils.sdf.parse(startDate);
            } catch (ParseException e) {
                txtStart.setBackgroundResource(R.drawable.border_red);
                return "Invalid start-time format! Please use dd/MM/yyyy HH:mm:ss.";
            }

        if (userId <= 0) {
            return "Something wrong!! Please login again.";
        }
        if (swComplete.isChecked()) {
            Date date = null;
            try {
                Date start = Utils.sdf.parse(startDate);
                date = new Date();
                assert start != null;
                if (start.after(date)){
                    return "Invalid";
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            complete = Utils.sdf.format(date);
        }
        if (!Objects.isNull(t)) {
            if (t.getTitle().equals(title) && t.getDescription().equals(description)
                    && t.getStartTime().equals(startDate) && Objects.equals(t.getCompleted(), complete)
                    && Objects.equals(t.getAudioID(), audioId))
                return "No thing to update";
            else {
                if (dbHelper.updateTask(new Task(id, title, description, startDate, complete, userId, cateId, audioId)))
                    return "Update successful";
                else
                    return "Update failed";
            }
        }
        return null;
    }

    public String deleteTask() {
        String id = txtId.getText().toString();
        if (dbHelper.deleteTask(id)) {
            return "Delete successful";
        } else {
            return "Delete failed";
        }
    }
    private void showConfirmDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm delete!!");
        builder.setMessage("Delete this task now?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String status = deleteTask();
                Toast.makeText(TaskDetailActivity.this, status, Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                bundle.putString("status",status);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}