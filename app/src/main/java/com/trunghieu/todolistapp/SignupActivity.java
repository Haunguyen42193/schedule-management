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

                if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(repass)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (dbHelper.checkEmail(email)) {
                    Toast.makeText(SignupActivity.this, "User already exists, please login", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(name, email, pass);
                    Boolean insert = dbHelper.insertUser(user);
                    if (insert) {
                        Toast.makeText(SignupActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignupActivity.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
