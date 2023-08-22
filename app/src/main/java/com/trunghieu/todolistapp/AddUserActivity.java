package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.User;

public class AddUserActivity extends AppCompatActivity {
        private EditText edtName, edtEmail, edtPass, edtRole;
        private Button addButton, btnBack;
        private DBHelper dbHelper;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

            edtName = findViewById(R.id.admin_add_edtName);
            edtEmail = findViewById(R.id.admin_add_edtEmail);
            edtPass = findViewById(R.id.admin_add_edtPass);
            edtRole = findViewById(R.id.admin_add_role);
            addButton = findViewById(R.id.admin_addUser_btn);
            btnBack = findViewById(R.id.btnBackAddUser);
            dbHelper = new DBHelper(this);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = edtName.getText().toString();
                    String email = edtEmail.getText().toString();
                    String pass = edtPass.getText().toString();
                    int role = Integer.parseInt(edtRole.getText().toString());

                    // Trước khi thêm, kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
                    if (dbHelper.checkEmail(email)) {
                        // Nếu email đã tồn tại, hiển thị thông báo lỗi
                        Toast.makeText(AddUserActivity.this, "Email đã tồn tại trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        // Nếu email chưa tồn tại, tiến hành thêm người dùng mới vào cơ sở dữ liệu
                        User newUser = new User(name, email, pass, role);
                        boolean isInserted = dbHelper.insertUser(newUser);

                        if (isInserted) {
                            // Nếu thêm thành công, hiển thị thông báo và quay lại ListUserActivity
                            Toast.makeText(AddUserActivity.this, "Người dùng đã được thêm mới", Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            // Nếu thêm không thành công, hiển thị thông báo lỗi
                            Toast.makeText(AddUserActivity.this, "Có lỗi xảy ra khi thêm người dùng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }
}