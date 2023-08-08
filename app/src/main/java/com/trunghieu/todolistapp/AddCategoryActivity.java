package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Category;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText edtName,  edtDescription;
    private Button addButton;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        edtName = findViewById(R.id.edtAddCategory_Name);
        edtDescription = findViewById(R.id.edtAddCategory_Description);
        addButton = findViewById(R.id.admin_addCategory_btn);
        dbHelper = new DBHelper(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();
                Category category = new Category(name, description);
                boolean isInserted = dbHelper.insertCategory(category);

                if (isInserted) {

                    Toast.makeText(AddCategoryActivity.this, "Danh mục đã được thêm mới", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCategoryActivity.this, ListCategoryActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(AddCategoryActivity.this, "Có lỗi xảy ra khi thêm danh mục", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
