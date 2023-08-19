package com.trunghieu.todolistapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trunghieu.todolistapp.adapter.AudioAdapter;
import com.trunghieu.todolistapp.adapter.CategoryAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Audio;
import com.trunghieu.todolistapp.model.Category;

import java.util.ArrayList;
import java.util.List;

public class ListAudioActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private AudioAdapter audioAdapter;
    private ArrayList<Audio> arrayList;
    private ArrayList<Audio> originalList;
    FloatingActionButton floatingActionButton;
    Button btnBack;
    androidx.appcompat.widget.SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_audio);
        searchView= findViewById(R.id.search_audio);
        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerViewAudio);
        btnBack = findViewById(R.id.btnBackListAudio);
        floatingActionButton = findViewById(R.id.add_buttonAudio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(audioAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListAudioActivity.this, AddAudioActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
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
                    audioAdapter.notifyDataSetChanged();
                }else {
                    filterAudio(newText);
                }
                return true;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if(data !=null && data.hasExtra("updateAudio")){
                Audio updateAudio = (Audio) data.getSerializableExtra("updateAudio");
                for( int i = 0; i < arrayList.size(); i ++){
                    Audio audio = arrayList.get(i);
                    if(audio.getId().equals(updateAudio.getId())){
                        arrayList.set(i, updateAudio);
                        break;
                    }
                }
                audioAdapter.updateAudioList(arrayList);
                Toast.makeText(this, "Danh sách âm thanh đã được cập nhật", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAudioData();
    }
    private void showAudioData() {
        arrayList = dbHelper.getAudioData();
        originalList = new ArrayList<>(arrayList);
        audioAdapter = new AudioAdapter(this,arrayList);
        recyclerView.setAdapter(audioAdapter);
    }
    private void filterAudio(String query) {
        ArrayList<Audio> filteredList = new ArrayList<>();
        for (Audio audio : arrayList) {

            if (audio.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(audio);
            }
        }
        audioAdapter.updateAudioList(filteredList);
    }

}