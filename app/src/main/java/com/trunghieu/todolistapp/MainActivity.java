package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.Task;
import com.trunghieu.todolistapp.model.User;


public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnSignup;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
//        dbHelper.insertUser(new User("m", "1", "1", 1));
//        dbHelper.insertTask(new Task("Do exercise", "done", "02/08/2023 06:00:00", "02/08/2023 06:30:00", 1, null));
//        dbHelper.insertTask(new Task("Do exercise", "squad", "03/08/2023 06:30:00", null, 1, null));
//        dbHelper.insertCategory(new Category("Work", "Some things to do in your work"));
//        dbHelper.insertCategory(new Category("Study", "Some things to do like exercise"));
//        dbHelper.insertCategory(new Category("Play", "Some things to play like video game"));
        btnLogin = findViewById(R.id.btn_main_login);
        btnSignup = findViewById(R.id.btn_main_signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void openSignUpActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}