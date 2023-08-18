package com.trunghieu.todolistapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trunghieu.todolistapp.adapter.TaskUserAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Audio;
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.Task;
import com.trunghieu.todolistapp.model.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserActivity extends AppCompatActivity{
    private CalendarView cldMain;
    private DBHelper dbHelper;
    private LinearLayout linearCate;
    private ArrayList<Button> listButton;

    private ArrayList<Audio> listAudio;
    private ArrayList<Category> listCate;
    private TaskUserAdapter taskUserAdapter;
    private ArrayList<Task> listTask;
    private Task task;
    private String userName;
    private RecyclerView rvItemTaskUser;
    private TextView txtNoticeUser;
    private User userLogin;
    private TextView txtSelectedTime;
    private Calendar calendar;
    private FloatingActionButton fltUserAddTaskButton, fltUserAddCateButton;
    private ActivityResultLauncher<Intent> launcher;
    private Button btnHistory, btnUserListCate;
    private MediaPlayer mediaPlayer;
    private Button btnBack;

    @SuppressLint({"MissingInflatedId", "ResourceType", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        dbHelper = new DBHelper(this);
        //Lấy ra user đang đăng nhập
        SharedPreferences preferences = getSharedPreferences("session", MODE_PRIVATE);
        userLogin = dbHelper.getUserById(preferences.getInt("user-id", -1));
        if(userLogin!=null) {
            userLogin = dbHelper.getUserById(userLogin.getId());
        }

        btnBack = findViewById(R.id.btnBack3);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Lấy các phần tử

        cldMain = (CalendarView) findViewById(R.id.cldMain);
        linearCate = (LinearLayout) findViewById(R.id.linearCate);
        rvItemTaskUser = (RecyclerView) findViewById(R.id.rvItemTaskUser);
        listCate = new ArrayList<>();
        listButton = new ArrayList<>();
        //lấy danh sách audio
        listAudio = dbHelper.getAudioData();
        listCate = dbHelper.getCategoriesData();
        listTask = new ArrayList<>();
        listTask = dbHelper.getTaskData(userLogin.getEmail());
        taskUserAdapter = new TaskUserAdapter(listTask, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        txtNoticeUser = (TextView) findViewById(R.id.txtNoticeUser);
        txtSelectedTime = (TextView) findViewById(R.id.txtSelectedTime);
        calendar = Calendar.getInstance();
        fltUserAddTaskButton = (FloatingActionButton) findViewById(R.id.fltUserAddTaskButton);

        //tìm id addcatebutton
        fltUserAddCateButton = (FloatingActionButton) findViewById(R.id.fltUserAddCateButton);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnUserListCate = (Button) findViewById(R.id.btnUserListCate);
        if(!listTask.isEmpty()) {
            rvItemTaskUser.setVisibility(View.VISIBLE);
            txtNoticeUser.setVisibility(View.GONE);
        } else {
            txtNoticeUser.setText("No task to do");
            rvItemTaskUser.setVisibility(View.GONE);
            txtNoticeUser.setVisibility(View.VISIBLE);
        }
        Calendar selectedCalendar = Calendar.getInstance();
        String d = Utils.sdf.format(selectedCalendar.getTime());
        txtSelectedTime.setText(d);
        //Bắt sự kiện chọn ngày trên calendar
        cldMain.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedCalendar = Calendar.getInstance();
                Date selectedDate;
                try {
                    selectedDate = Utils.sdf.parse(txtSelectedTime.getText().toString());
                    selectedCalendar.setTime(selectedDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                selectedCalendar.set(year, month, dayOfMonth);
                selectedDate = selectedCalendar.getTime();
                txtSelectedTime.setText(Utils.sdf.format(selectedDate));
                updateListOnListener(selectedDate);
            }
        });

        //Thiết lập các item cho recyclerView
        rvItemTaskUser.setLayoutManager(layoutManager);
        rvItemTaskUser.setAdapter(taskUserAdapter);

        setOnClickRecyclerView();

        newButtonCate();
        //chọn tg
        txtSelectedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Date date = Utils.sdf.parse(txtSelectedTime.getText().toString());
                    calendar.setTime(date);
                    showTimePicker(UserActivity.this, calendar, txtSelectedTime);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        launcher= registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK){
                Intent data = result.getData();
                assert data != null;
                Bundle bundle = data.getExtras();
                String status = bundle.getString("status");
                if(status != null) {
                    recreate();
                }
            }
        });
        fltUserAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fltUserAddTaskButton.getId() == v.getId()){
                    Intent intent = new Intent(UserActivity.this, AddTaskActivity.class);
                    startActivity(intent);
                }
            }
        });
        //user add category
        fltUserAddCateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fltUserAddCateButton.getId() == v.getId()){
                    Intent intent = new Intent(UserActivity.this,UserAddCategoryActivity.class);
                    startActivity(intent);
                }
            }
        });
        //user list cate
        btnUserListCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, UserDetailCategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user-mail", userLogin.getEmail());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, HistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user-mail", userLogin.getEmail());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void newButtonCate() {
        //Tạo các button là các category
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 10, 0);
        Button btnAll = new Button(this);
        btnAll.setText("ALL");
        btnAll.setTextSize(14);
        btnAll.setId(0);
        btnAll.setBackgroundResource(R.drawable.button_background);
        btnAll.setLayoutParams(layoutParams);
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTask = dbHelper.getTaskData(userLogin.getEmail());
                taskUserAdapter.updateTaskList(listTask);
            }
        });
        listButton.add(btnAll);
        linearCate.addView(btnAll);
        if (listCate != null) {
            int i = 1;
            for (Category cate: listCate) {
                Button button = new Button(this);
                button.setText(cate.getName());
                button.setTextSize(14);
                button.setId(i++);
                button.setBackgroundResource(R.drawable.button_background);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listTask = dbHelper.getTaskByCategoryIdUserId(cate.getId(), userLogin.getId());
                        taskUserAdapter.updateTaskList(listTask);
                    }
                });
                listButton.add(button);
                linearCate.addView(button);
            }
        }
    }
    public boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public boolean isSameTime(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return calendar1.get(Calendar.HOUR) == calendar2.get(Calendar.HOUR) &&
                calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE) &&
                isSameDay(date1, date2);
    }
    public void updateListOnListener(Date date) {
        listTask = dbHelper.getTaskData(userLogin.getEmail());
        ArrayList<Task> newListTask = new ArrayList<>();
            for(Task task:listTask) {
            try {
                Date taskDate = Utils.sdf.parse(task.getStartTime());
                if(isSameDay(date, taskDate)) {
                    newListTask.add(task);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        taskUserAdapter.updateTaskList(newListTask);
    }
    public void updateListOnListenerTime(Date date) {
        listTask = dbHelper.getTaskData(userLogin.getEmail());
        ArrayList<Task> newListTask = new ArrayList<>();
        for(Task task:listTask) {
            try {
                Date taskDate = Utils.sdf.parse(task.getStartTime());
                if(isSameTime(date, taskDate)) {
                    newListTask.add(task);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        taskUserAdapter.updateTaskList(newListTask);
    }
    public void showTimePicker(Context context, Calendar calendar, TextView txt) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        String formatDate = Utils.sdf.format(calendar.getTime());
                        txt.setText(formatDate);
                        updateListOnListenerTime(calendar.getTime());
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }


    public void setOnClickRecyclerView() {
        taskUserAdapter.setOnItemClickListener(new TaskUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(UserActivity.this, TaskDetailActivity.class);
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

            @Override
            public void onCheckBoxClick(Task task, CheckBox cb) {
                Task t= task;
                Date date = null;
                try {
                    Date start = Utils.sdf.parse(t.getStartTime());
                    date = new Date();
                    assert start != null;
                    if (start.after(date)){
                        Toast.makeText(UserActivity.this, "Invalid", Toast.LENGTH_LONG).show();
                        cb.setChecked(false);
                        return;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                t.setCompleted(Utils.sdf.format(date));
                if(cb.isChecked())
                    cb.setEnabled(false);
                dbHelper.updateTask(t);
                Toast.makeText(UserActivity.this, "Task completed", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void checkTaskDeadline(Audio audio) throws ParseException {
        // Kiểm tra thời gian đến hạn của task
        long taskDeadline = Utils.sdf.parse(task.getStartTime()).getTime()/1000;
        if (taskDeadline <= System.currentTimeMillis()) {
            // Tạo đối tượng Audio đại diện cho âm thanh thông báo
            //Intent intent = new Intent(this, NotificationReceiver.class);
            //intent.putExtra("audio_name", audio.getName());

            // Gọi phương thức scheduleAlarmForTask() trong AudioNotificationHelper
        }
    }

}