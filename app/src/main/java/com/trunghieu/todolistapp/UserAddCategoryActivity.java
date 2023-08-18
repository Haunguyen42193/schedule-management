package com.trunghieu.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Category;

public class UserAddCategoryActivity extends AppCompatActivity {
    private EditText edtUserAddCateName, edtUserAddCateDes;
    private Button btn_UserAddCate;

    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_category);
        //tim id editName, editDes, button add
        edtUserAddCateName = findViewById(R.id.edtUserAddCategory_Name);
        edtUserAddCateDes = findViewById(R.id.edtUserAddCategory_Description);
        btn_UserAddCate = findViewById(R.id.admin_userAddCategory_btn);
        dbHelper = new DBHelper(this);

        btn_UserAddCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtUserAddCateName.getText().toString();
                String description = edtUserAddCateDes.getText().toString();
                Category userNewCate = new Category(name, description);
                boolean isInserted = dbHelper.insertCategory(userNewCate);
                if(isInserted){
                    Toast.makeText(UserAddCategoryActivity.this, "Thẻ đã được thêm mới", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserAddCategoryActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UserAddCategoryActivity.this, "Có lỗi xảy ra khi thêm thẻ", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
