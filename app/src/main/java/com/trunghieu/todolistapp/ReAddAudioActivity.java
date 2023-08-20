package com.trunghieu.todolistapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trunghieu.todolistapp.adapter.AudioAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Audio;

import java.util.List;
import java.util.UUID;

public class ReAddAudioActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private String selectedAudioFilePath;
    EditText edtName;
    EditText edtFilePath;
    Button addButton, selectButton, btnBackAddAudio;
    ActivityResultLauncher<String> pickAudioLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_audio);
        dbHelper = new DBHelper(this);
        edtName = findViewById(R.id.admin_add_edtNameAudio);
        edtFilePath= findViewById(R.id.admin_add_edtFileAudio);
        addButton = findViewById(R.id.button_add_audio);
        btnBackAddAudio = findViewById(R.id.btnBackAddAudio);
        btnBackAddAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        selectButton = findViewById(R.id.btn_ChoiceAudioFile);
        pickAudioLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            selectedAudioFilePath = result.toString();
                            edtFilePath.setText(selectedAudioFilePath); // Gán đường dẫn tệp đã chọn
                            Toast.makeText(ReAddAudioActivity.this, "Đã chọn tệp âm thanh!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        selectButton.setOnClickListener(v -> pickAudioFile());
        addButton.setOnClickListener(v -> saveAudioToDatabase());
    }
    private void pickAudioFile() {
        pickAudioLauncher.launch("audio/*");
    }
    private void saveAudioToDatabase() {
        String audioName = edtName.getText().toString();
        String filePath = edtFilePath.getText().toString();
        Audio audio = new Audio(audioName, filePath);
        boolean isInserted = dbHelper.insertAudio(audio);
        if (isInserted) {
            Toast.makeText(ReAddAudioActivity.this, "Âm thanh đã được thêm mới", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ReAddAudioActivity.this, AddTaskActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(ReAddAudioActivity.this, "Có lỗi xảy ra khi thêm âm thanh", Toast.LENGTH_SHORT).show();
        }

    }



}