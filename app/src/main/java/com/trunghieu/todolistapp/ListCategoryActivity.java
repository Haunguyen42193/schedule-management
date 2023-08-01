package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trunghieu.todolistapp.adapter.CategoryAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Category;

import java.util.ArrayList;

public class ListCategoryActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayList<Category> arrayList;
    CategoryAdapter categoryAdapter;
    private FloatingActionButton addButton;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
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

    }
    private void ShowCategoryData(){


    }

}