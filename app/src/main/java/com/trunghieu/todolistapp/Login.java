package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.databinding.ActivityLoginBinding;
import com.trunghieu.todolistapp.model.User;


public class Login extends AppCompatActivity {


    ActivityLoginBinding binding;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtEmailLogin.getText().toString();
                String pass = binding.edtPassLogin.getText().toString();
                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checking = dbHelper.checkEmailPassword(email, pass);
                    if (checking) {
                        User user = dbHelper.getUserByEmail(email);
                        if (user.getRole() == 1) {
                            // Nếu role là 1 (Admin), chuyển đến AdminManageActivity
                            Toast.makeText(Login.this, "Login successful as Admin!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("UserName", user.getName());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            // Nếu role không phải là 1 (User), chuyển đến MainActivity
                            Toast.makeText(Login.this, "Login successful as User!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(Login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    }

