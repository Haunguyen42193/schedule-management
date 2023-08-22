package com.trunghieu.todolistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Audio;
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.Task;
import com.trunghieu.todolistapp.model.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity {
    private EditText edtTitleAdd;
    private EditText edtDesAdd;
    private TextView txtStartAdd;
    private Spinner spnCateAdd, spnListAudio;
    private String[] items;
    private DBHelper dbHelper;
    private Calendar calendar;
    private ArrayList<Category> listCate;
    private ArrayList<Audio> listAudio;
    private Category selectedCate;
    private Audio selectedAudio;
    private Button btnAddTaskDetail, btnUserUploadAudio;
    private User userLogin;

    private Audio userLogin1;


    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //Lấy các đối tượng giao diện
        edtTitleAdd = (EditText) findViewById(R.id.txtTitleAdd);
        edtDesAdd = (EditText) findViewById(R.id.txtDesAdd);
        txtStartAdd = (TextView) findViewById(R.id.txtStartAdd);
        spnCateAdd = (Spinner) findViewById(R.id.spnCateAdd);
        spnListAudio = (Spinner) findViewById(R.id.spnListAudio);
        calendar = Calendar.getInstance();
        btnAddTaskDetail = (Button) findViewById(R.id.btnAddTaskDetail);

        //btn upload audio
        btnUserUploadAudio = (Button) findViewById(R.id.btn_UserUploadAudio);

        btnBack = findViewById(R.id.btnBack6);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtStartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDateTimePicker(AddTaskActivity.this, calendar, txtStartAdd);
            }
        });

        dbHelper = new DBHelper(this);
        listCate = dbHelper.getCategoriesData();
        SharedPreferences provider = getSharedPreferences("session", MODE_PRIVATE);
        userLogin = dbHelper.getUserById(provider.getInt("user-id", -1));

        if(!listCate.isEmpty()){
            items = new String[listCate.size()];
            int i = 0;
            for (Category cate: listCate) {
                items[i++] = cate.getName();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCateAdd.setAdapter(arrayAdapter);
            setUpSpinnerCate();
        }
        //
        dbHelper = new DBHelper(this);
        listAudio = dbHelper.getAudioData();
        SharedPreferences providerAudio = getSharedPreferences("session", MODE_PRIVATE);
        userLogin1 = dbHelper.getAudioByID(String.valueOf(providerAudio.getInt("audio-id", -1)));

        if(!listAudio.isEmpty()){
            items = new String[listAudio.size()];
            int i = 0;
            for (Audio audio: listAudio) {
                items[i++] = audio.getName();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnListAudio.setAdapter(arrayAdapter);
            setUpSpinnerListAudio();

        }
        //xu ly su kien click nut upload
        btnUserUploadAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTaskActivity.this, AddAudioActivity.class);
                startActivity(intent);
            }
        });
        btnAddTaskDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = addTask();
                Toast.makeText(AddTaskActivity.this, status, Toast.LENGTH_LONG).show();
                if(Objects.equals(status, "Add successfully!!")) {
                    Intent intent = getIntent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
    private void setUpSpinnerCate () {
        spnCateAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCate = listCate.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //
    private void setUpSpinnerListAudio() {
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
    private boolean checkDuplicateTaskName(String title) {
        // Truy vấn cơ sở dữ liệu để kiểm tra task có cùng tên không
        boolean isDuplicate = dbHelper.checkTitleExists(title);

        return isDuplicate;
    }
    public String addTask() {
        String title = edtTitleAdd.getText().toString();
        String description = edtDesAdd.getText().toString();
        String startDate = txtStartAdd.getText().toString();
        String selectedAudioID = selectedAudio.getId();
        int userId =
                userLogin.getId();
        String cateId = selectedCate.getId();
        if (checkDuplicateTaskName(title)) {
            return "Task with the same name already exists!";
        }
        if (TextUtils.isEmpty(title)) {
            edtTitleAdd.setBackgroundResource(R.drawable.border_red);
            return "Input title, please!";
        } else {
            edtTitleAdd.setBackgroundResource(R.drawable.border);
        }

        if (TextUtils.isEmpty(startDate)) {
            txtStartAdd.setBackgroundResource(R.drawable.border_red);
            return "Chose start-time, please!";
        } else
            try {
                txtStartAdd.setBackgroundResource(R.drawable.border);
                Date date = Utils.sdf.parse(startDate);
            } catch (ParseException e) {
                txtStartAdd.setBackgroundResource(R.drawable.border_red);
                return "Invalid start-time format! Please use dd/MM/yyyy HH:mm:ss.";
            }

        if (userId <= 0) {
            return "Something wrong!! Please login again.";
        }
        boolean insertState = dbHelper.insertTask(new Task(title, description, startDate, null, userId, cateId, selectedAudioID));

        if (insertState) return "Add successfully!!";
        else return "Add failed!!!";
    }
}