package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.User;
import com.trunghieu.todolistapp.databinding.ActivitySignupBinding;


public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    DBHelper dbHelper;
    private int role =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new DBHelper(this);

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edtNameSignup.getText().toString();
                String email = binding.edtEmailSignup.getText().toString();
                String pass = binding.edtPassSignup.getText().toString();
                String repass = binding.edtRepassSignup.getText().toString();


                boolean isValidName = !name.isEmpty();
                boolean isValidEmail = isValidEmail(email);
                boolean isValidPassword = isValidPassword(pass);
                boolean isPasswordMatch = pass.equals(repass);
                boolean isUserExists = dbHelper.checkEmail(email);

                if (isValidName && isValidEmail && isValidPassword && isPasswordMatch && !isUserExists) {
                    User user = new User(name, email, pass, role);
                    Boolean insert = dbHelper.insertUser(user);
                    if (insert) {
                        Toast.makeText(SignupActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ListUserActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignupActivity.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!isValidName) {
                        Toast.makeText(SignupActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                    } else if (!isValidEmail) {
                        Toast.makeText(SignupActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    } else if (!isValidPassword) {
                        Toast.makeText(SignupActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    } else if (!isPasswordMatch) {
                        Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    } else if (isUserExists) {
                        Toast.makeText(SignupActivity.this, "User already exists, please login", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

}
