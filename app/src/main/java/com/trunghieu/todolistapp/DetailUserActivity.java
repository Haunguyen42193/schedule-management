package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.User;

import java.util.ArrayList;
    public class DetailUserActivity extends AppCompatActivity {
        EditText edtName, edtEmail, edtPass, edtRole;
        DBHelper dbHelper;
        String userEmail;
        Button btnUpdate, btnDelete;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_user);
            edtName = findViewById(R.id.admin_detail_edtNameUser);
            edtEmail = findViewById(R.id.admin_detail_edtEmailUser);
            edtPass = findViewById(R.id.admin_detail_edtPassUser);
            edtRole = findViewById(R.id.admin_detail_edtRoleUser);
            btnUpdate = findViewById(R.id.button_update_user);
            btnDelete = findViewById(R.id.button_delete_user);
            dbHelper = new DBHelper(this);

            // Get the user's email from the Intent
            userEmail = getIntent().getStringExtra("email");

            // Get the user's information based on the email and display it on the screen
            User currentUser = dbHelper.getUserByEmail(userEmail);
            if (currentUser != null) {
                edtName.setText(currentUser.getName());
                edtEmail.setText(currentUser.getEmail());
                edtPass.setText(currentUser.getPassword());
                edtRole.setText(String.valueOf(currentUser.getRole()));
            }
            // Set the click event for the "Update" button
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpdateClicked(v);
                }
            });


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(v);
                }
            });
        }
        public void onUpdateClicked(View view) {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String pass = edtPass.getText().toString();
            int role = Integer.parseInt(edtRole.getText().toString()); // Convert to integer

            // Create a new User object with the updated information
            User updatedUser = new User(userEmail, name, email, pass, role);

            // Update the user's information in the database
            boolean isUpdated = dbHelper.updateUserByEmail(userEmail, updatedUser);

            if (isUpdated) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedUser", updatedUser);
                setResult(RESULT_OK, resultIntent);
                finish();
                Toast.makeText(this, "Thông tin người dùng đã được cập nhật.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Có lỗi xảy ra khi cập nhật thông tin người dùng.", Toast.LENGTH_SHORT).show();
            }
        }
        public void onDeleteClicked(View view) {
            // Lấy email của người dùng hiện tại
            String email = edtEmail.getText().toString();

            // Xóa người dùng từ cơ sở dữ liệu
            boolean isDeleted = dbHelper.deleteUserByEmail(email);

            if (isDeleted) {
                Toast.makeText(this, "Người dùng đã được xóa.", Toast.LENGTH_SHORT).show();
                // Quay lại ListUserActivity sau khi xóa thành công
                finish();
            } else {
                Toast.makeText(this, "Có lỗi xảy ra khi xóa người dùng.", Toast.LENGTH_SHORT).show();
            }
        }
    }