package com.trunghieu.todolistapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trunghieu.todolistapp.adapter.CategoryAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.User;

import java.util.ArrayList;

public class ListCategoryActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private ArrayList<Category> arrayList;
    private ArrayList<Category> originalList;
    private CategoryAdapter categoryAdapter;
    private FloatingActionButton addButton;
    androidx.appcompat.widget.SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        searchView= findViewById(R.id.search_category);
        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerViewCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        addButton = findViewById(R.id.add_buttonCategory);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCategoryActivity.this, AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    arrayList.clear();
                    arrayList.addAll(originalList);;
                    categoryAdapter.notifyDataSetChanged();
                }else {
                    filterCategory(newText);
                }
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if(data !=null && data.hasExtra("updateCategory")){
                Category updateCategory = (Category) data.getSerializableExtra("updateCategory");
                for( int i = 0; i < arrayList.size(); i ++){
                    Category category = arrayList.get(i);
                    if(category.getId().equals(updateCategory.getId())){
                        arrayList.set(i, updateCategory);
                        break;
                    }
                }
                categoryAdapter.updateCategoryList(arrayList);
                Toast.makeText(this, "List category updated", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Đặt lại danh sách danh mục sau khi quay lại từ DetailCategoryActivity
        ShowCategoryData();
    }
    private void ShowCategoryData() {
        arrayList = dbHelper.getCategoryData();
        originalList = new ArrayList<>(arrayList);

        categoryAdapter = new CategoryAdapter(this, arrayList);
        recyclerView.setAdapter(categoryAdapter);
    }
    private void filterCategory(String query) {
        // Tạo một danh sách tạm thời để lưu trữ kết quả tìm kiếm
        ArrayList<Category> filteredList = new ArrayList<>();
        for (Category category : arrayList) {

            if (category.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(category);
            }
        }
        // Cập nhật RecyclerView với danh sách kết quả tìm kiếm
        categoryAdapter.updateCategoryList(filteredList);
    }
}
