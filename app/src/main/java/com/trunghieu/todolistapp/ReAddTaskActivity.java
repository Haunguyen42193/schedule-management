package com.trunghieu.todolistapp;

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
import java.util.Objects;

public class ReAddTaskActivity extends AppCompatActivity {
    private EditText edtTitleReAdd;
    private EditText edtDesReAdd;
    private TextView txtStartReAdd;
    private Spinner spnCateReAdd;
    private String[] items;
    private DBHelper dbHelper;
    private Calendar calendar;
    private ArrayList<Category> listCate;
    private Category selectedCate;
    private Button btnReAddTaskDetail;
    private User userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_add_task);

        //Lấy các đối tượng giao diện
        dbHelper = new DBHelper(this);
        edtTitleReAdd = (EditText) findViewById(R.id.txtTitleReAdd);
        edtDesReAdd = (EditText) findViewById(R.id.txtDesReAdd);
        txtStartReAdd = (TextView) findViewById(R.id.txtStartReAdd);
        spnCateReAdd = (Spinner) findViewById(R.id.spnCateReAdd);
        calendar = Calendar.getInstance();
        btnReAddTaskDetail = (Button) findViewById(R.id.btnReAddTaskDetail);
        SharedPreferences preferences = getSharedPreferences("session", MODE_PRIVATE);
        userLogin = dbHelper.getUserById(preferences.getInt("user-id", -1));

        //Gán giá trị các thuộc tính từ trang detail cho các ô trong readd
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        edtDesReAdd.setText(bundle.getString("re-add-task-description"));
        edtTitleReAdd.setText(bundle.getString("re-add-task-title"));
        txtStartReAdd.setText(bundle.getString("re-add-task-start"));

        //Thiết lập giá trị sẵn có cho lịch
        Date startDate;
        try {
            startDate = Utils.sdf.parse(bundle.getString("re-add-task-start"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        calendar.setTime(startDate);

        //Hiển thị lịch để cho người dùng chọn
        txtStartReAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDateTimePicker(ReAddTaskActivity.this, calendar, txtStartReAdd);
            }
        });

        //Hiển thị các danh mục
        listCate = dbHelper.getCategoriesData();
        if(!listCate.isEmpty()){
            items = new String[listCate.size()];
            int i = 0;
            int position = 0;
            for (Category cate: listCate) {
                if(Objects.equals(cate.getName(), bundle.getString("re-add-task-cate")))
                    position = i;
                items[i++] = cate.getName();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCateReAdd.setAdapter(arrayAdapter);
            spnCateReAdd.setSelection(position);
            setUpSpinnerCate();
        }

        //Sự kiện click vào nút add
        btnReAddTaskDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = addTask();
                Toast.makeText(ReAddTaskActivity.this, status, Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
    //Thiết lập lấy giá trị category do người dùng chọn
    private void setUpSpinnerCate () {
        spnCateReAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        String title = edtTitleReAdd.getText().toString();
        String description = edtDesReAdd.getText().toString();
        String startDate = txtStartReAdd.getText().toString();
        int userId = userLogin.getId();
        String cateId = selectedCate.getId();
        if (TextUtils.isEmpty(title)) {
            edtTitleReAdd.setBackgroundResource(R.drawable.border_red);
            return "Input title, please!";
        } else {
            edtTitleReAdd.setBackgroundResource(R.drawable.border);
        }

        if (TextUtils.isEmpty(startDate)) {
            txtStartReAdd.setBackgroundResource(R.drawable.border_red);
            return "Chose start-time, please!";
        } else
            try {
                txtStartReAdd.setBackgroundResource(R.drawable.border);
                Date date = Utils.sdf.parse(startDate);
            } catch (ParseException e) {
                txtStartReAdd.setBackgroundResource(R.drawable.border_red);
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