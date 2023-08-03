package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trunghieu.todolistapp.adapter.TaskAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.Task;
import com.trunghieu.todolistapp.model.User;

import java.util.ArrayList;

public class ListTaskActivity extends AppCompatActivity {
    private ArrayList<Task> listTask = new ArrayList<>();
    private TaskAdapter adapter;
    private RecyclerView recyclerView;
    private DBHelper dbHelper;
    private Spinner spnUser;
    private String[] item;
    private ArrayList<User> listUser;
    private TextView txtNotic;
    private FloatingActionButton btnAddTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);

        //tìm các thành phần
        recyclerView = (RecyclerView) findViewById(R.id.rcvTaskListAdmin);
        adapter = new TaskAdapter(listTask, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ListTaskActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        txtNotic = findViewById(R.id.txtNotic);
        spnUser = findViewById(R.id.spnUser);
        btnAddTask = (FloatingActionButton) findViewById(R.id.btnAddTask);

        //lấy danh sách cần hiển thị
        dbHelper = new DBHelper(this);
        listUser = dbHelper.getUserData();

        //kiểm tra danh sách có rỗng không
        if(!listUser.isEmpty()) {
            item = new String[listUser.size()];
            int i = 0;
            for (User user : listUser) {
                item[i++] = "Name: " + user.getName() + " / Email: " + user.getEmail();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnUser.setAdapter(arrayAdapter);
            setUpSpinner();
        }
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnAddTask.getId() == v.getId()){
                    Intent intent = new Intent(ListTaskActivity.this, AddTaskActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setUpSpinner() {
        spnUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerSelected(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void onSpinnerSelected(int position) {
        listTask = dbHelper.getTaskData(listUser.get(position).getEmail());
        adapter.updateTaskList(listTask);
        onClickItemRecyclerView();
        if(!listTask.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            txtNotic.setVisibility(View.GONE);
        } else {
            txtNotic.setText("Không có lịch trình!!!!");
            recyclerView.setVisibility(View.GONE);
            txtNotic.setVisibility(View.VISIBLE);
        }
    }

    public void onClickItemRecyclerView() {
        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(ListTaskActivity.this, TaskDetailActivity.class);
                Category c = dbHelper.getCategoryById(task.getCategoryID());
                User u = dbHelper.getUserById(task.getUserId());
                Bundle bundle = new Bundle();
                bundle.putString("task-id", task.getId());
                bundle.putString("task-title", task.getTitle());
                bundle.putString("task-description", task.getDescription());
                bundle.putString("task-start", task.getStartTime());
                if (task.getCompleted() != null) {
                    bundle.putString("task-complete", task.getCompleted());
                } else {
                    bundle.putString("task-complete", "Time not set");
                }
                if (c != null) {
                    bundle.putString("task-cate", c.getName());
                } else {
                    bundle.putString("task-cate", "Not found");
                }
                if (u != null) {
                    bundle.putString("task-user", u.getName());
                } else {
                    bundle.putString("task-user", "Not found");
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}