package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {
    private LinearLayout lnHistory;
    private DBHelper dbHelper;
    private ArrayList<Task> listTask;
    private Button btnSort;
    private ArrayList<Task> listTaskConplete;
    private Button btnBack;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle bundle = getIntent().getExtras();


        lnHistory = (LinearLayout) findViewById(R.id.lnHistory);
        btnSort = (Button) findViewById(R.id.btnSort);
        dbHelper = new DBHelper(this);
        listTask = new ArrayList<>();
        btnBack = findViewById(R.id.btnBack7);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        listTask = dbHelper.getTaskData(bundle.getString("user-mail"));
        final int[] isDecrease = {1};
        listTaskConplete = new ArrayList<>();

        for(Task task: listTask) {
            if(task.getCompleted() != null) {
                listTaskConplete.add(task);
            }
        }

        listTaskConplete.sort((o1, o2) -> {
            try {
                Date d1 = Utils.sdf.parse(o1.getCompleted());
                Date d2 = Utils.sdf.parse(o2.getCompleted());
                assert d2 != null;
                return d2.compareTo(d1);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSort.getText().toString().equals("Increase")) {
                    listTaskConplete.sort((o1, o2) -> {
                        try {
                            Date d1 = Utils.sdf.parse(o1.getCompleted());
                            Date d2 = Utils.sdf.parse(o2.getCompleted());
                            assert d1 != null;
                            return d1.compareTo(d2);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    btnSort.setText("Decrease");
                    isDecrease[0] = 1;
                } else {
                    listTaskConplete.sort((o1, o2) -> {
                        try {
                            Date d1 = Utils.sdf.parse(o1.getCompleted());
                            Date d2 = Utils.sdf.parse(o2.getCompleted());
                            assert d1 != null;
                            assert d2 != null;
                            return d2.compareTo(d1);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    btnSort.setText("Increase");
                    isDecrease[0] = 0;
                }
                int childCount = lnHistory.getChildCount();
                for (int i = 1; i < childCount; i++) {
                    lnHistory.removeViewAt(1);
                }
                createHistoryContent();
            }
        });
        createHistoryContent();

    }

    public void createHistoryContent() {
        int i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        for(Task task: listTaskConplete) {
            if (task.getCompleted() != null) {
                i++;
                StringBuilder t1 = new StringBuilder("");
                StringBuilder t2 = new StringBuilder("\t\t\t\t");
                StringBuilder t3 = new StringBuilder("\t\t\t\t\t\t");
                StringBuilder t4 = new StringBuilder("\t\t\t\t\t");
                StringBuilder t5 = new StringBuilder(task.getTitle());
                if((String.valueOf(i).length() - 2) > 0) {
                    for(int j = 0; j < (String.valueOf(i).length() - 2); j++) {
                        t1.replace(0, 1, "");
                    }
                }
                if(task.getTitle().length() - 4 > 0) {
                    for(int j = 0; j < (task.getTitle().length() - 4); j++) {
                        t3.replace(0, 1, "");
                        if(j==2)
                            t5.replace(j + 4, task.getTitle().length(),"");
                    }
                }
                TextView txtContent = new TextView(this);
                try {
                    txtContent.setText(t1 + String.valueOf(i)+ "\t" + t2+ "\t" +
                            t5+ "\t" + t3 + "\t" +
                            sdf.format(sdf.parse(task.getStartTime()))+ "\t" +
                            t4+ "\t" +
                            sdf.format(sdf.parse(task.getCompleted())));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                txtContent.setPadding(20, 10, 20, 10);
                txtContent.setTextSize(18);
                txtContent.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);

                lnHistory.addView(txtContent);
            }
        }
    }
}