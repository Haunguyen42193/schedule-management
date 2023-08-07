package com.trunghieu.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trunghieu.todolistapp.adapter.AudioAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Audio;

import java.util.List;

public class ListAudioActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private AudioAdapter audioAdapter;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_audio);

        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.recyclerViewAudio);
        floatingActionButton = findViewById(R.id.add_buttonAudio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Audio> audioList = dbHelper.getAllAudios();
        audioAdapter = new AudioAdapter(audioList);
        recyclerView.setAdapter(audioAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListAudioActivity.this, AddAudioActivity.class);
                startActivity(intent);
            }
        });
    }
    private List<Audio> getAudioDataFromDatabase() {
        return dbHelper.getAllAudios();
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Lấy danh sách dữ liệu âm thanh mới nhất từ cơ sở dữ liệu
        List<Audio> updatedAudioList = (getAudioDataFromDatabase());

        // Cập nhật RecyclerView với dữ liệu âm thanh mới nhất
        audioAdapter.setAudioList(updatedAudioList);
    }

}