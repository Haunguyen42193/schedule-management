package com.trunghieu.todolistapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trunghieu.todolistapp.adapter.UserAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.User;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    ArrayList<User> arrayList;
    UserAdapter adapter;
    private FloatingActionButton addButton;
    private ArrayList<User> originalList;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        searchView = findViewById(R.id.search_user);
        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // add user
        addButton = findViewById(R.id.add_button);
        // Thêm sự kiện OnClickListener cho nút FloatingActionButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi người dùng nhấn vào nút FloatingActionButton,
                // Chuyển đến AddUserActivity bằng Intent
                Intent intent = new Intent(ListUserActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });


        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    arrayList.clear();
                    arrayList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                } else {
                    // Ngược lại, thực hiện tìm kiếm và cập nhật RecyclerView
                    filterUsers(newText);
                }
                return true;
            }
        });
    }
    private void showUserData() {
        arrayList = dbHelper.getUserData();
        // Lưu danh sách gốc vào originalList
        originalList = new ArrayList<>(arrayList);
        // Tạo adapter và hiển thị RecyclerView
        adapter = new UserAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("updatedUser")) {
                User updatedUser = (User) data.getSerializableExtra("updatedUser");
                // Update the user information in the ArrayList
                for (int i = 0; i < arrayList.size(); i++) {
                    User user = arrayList.get(i);
                    if (user.getEmail().equals(updatedUser.getEmail())) {
                        arrayList.set(i, updatedUser);
                        break;
                    }
                }
                // Update the RecyclerView
                adapter.updateUserList(arrayList);
                // Add a log or Toast here to check if the RecyclerView has been updated with the new data
                // For example:
                Toast.makeText(this, "RecyclerView updated with the new user data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void filterUsers(String query) {
        // Tạo một danh sách tạm thời để lưu trữ kết quả tìm kiếm
        ArrayList<User> filteredList = new ArrayList<>();
        for (User user : arrayList) {
            // Kiểm tra tên của người dùng có chứa văn bản tìm kiếm không
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }
        // Cập nhật RecyclerView với danh sách kết quả tìm kiếm
        adapter.updateUserList(filteredList);
    }
    protected void onResume() {
        super.onResume();
        showUserData();
    }
}