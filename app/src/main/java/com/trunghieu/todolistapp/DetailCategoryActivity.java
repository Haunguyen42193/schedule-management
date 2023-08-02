package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trunghieu.todolistapp.adapter.CategoryAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.User;

public class DetailCategoryActivity extends AppCompatActivity {

    private Button btnDelete, btnUpdate;
    private EditText edtName, edtDescription;
    private CategoryAdapter categoryAdapter;
    private Category category;
    String categoryId;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);
        edtName = findViewById(R.id.admin_detail_edtNameCategory);
        edtDescription = findViewById(R.id.admin_detail_edtDescCategory);
        btnDelete = findViewById(R.id.button_delete_category);
        btnUpdate = findViewById(R.id.button_update_category);

        dbHelper = new DBHelper(this);

        categoryId = getIntent().getStringExtra("id");

        Category currentCategory = dbHelper.getCategoryByID(categoryId);
        if(currentCategory != null){
            edtName.setText(currentCategory.getName());
            edtDescription.setText(currentCategory.getdescription());
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClicked(v,currentCategory.getId());
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateClicked(v, categoryId);
            }
        });
    }
    public void onDeleteClicked (View view, String id){
        boolean isDeleted = dbHelper.deleteCategoryByID(id);

        if (isDeleted) {
            Toast.makeText(this, "Danh muc da duoc them moi.", Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Toast.makeText(this, "Có lỗi xảy ra khi xóa danh muc.", Toast.LENGTH_SHORT).show();
        }
    }
    public void onUpdateClicked(View view, String id) {
        String name = edtName.getText().toString();
        String desc = edtDescription.getText().toString();

        // Create a new User object with the updated information
        Category updatedCategory = new Category(id, name, desc);

        // Update the user's information in the database
        boolean isUpdated = dbHelper.updateCategoryByID(id, updatedCategory);

        if (isUpdated) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedUser", updatedCategory);
            setResult(RESULT_OK, resultIntent);
            finish();
            Toast.makeText(this, "Thông tin danh muc đã được cập nhật.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Có lỗi xảy ra khi cập nhật thông tin danh muc.", Toast.LENGTH_SHORT).show();
        }
    }


}



