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
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.Task;
import com.trunghieu.todolistapp.model.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    private EditText edtTitleAdd;
    private EditText edtDesAdd;
    private TextView txtStartAdd;
    private Spinner spnCateAdd;
    private String[] items;
    private DBHelper dbHelper;
    private Calendar calendar;
    private ArrayList<Category> listCate;
    private Category selectedCate;
    private Button btnAddTaskDetail;
    private User userLogin;
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
        calendar = Calendar.getInstance();
        btnAddTaskDetail = (Button) findViewById(R.id.btnAddTaskDetail);
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
        btnAddTaskDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = addTask();
                Toast.makeText(AddTaskActivity.this, status, Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                bundle.putString("status",status);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
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

    public String addTask() {
        String title = edtTitleAdd.getText().toString();
        String description = edtDesAdd.getText().toString();
        String startDate = txtStartAdd.getText().toString();
        int userId =
                userLogin.getId();
        String cateId = selectedCate.getId();
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
        boolean insertState = dbHelper.insertTask(new Task(title, description, startDate, null, userId, cateId));

        if (insertState) return "Add successfully!!";
        else return "Add failed!!!";
    }
}