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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trunghieu.todolistapp.adapter.AudioAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Audio;

import java.util.List;
import java.util.UUID;

public class AddAudioActivity extends AppCompatActivity {
    private static final int PICK_AUDIO_REQUEST_CODE = 1;
    private DBHelper dbHelper;
    private String selectedAudioFilePath;
    private RecyclerView recyclerView;
    private AudioAdapter audioAdapter;
    EditText edtName, edtFilePath;
    Button addButton, selectButton;
    ActivityResultLauncher<String> pickAudioLauncher;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_audio);
        dbHelper = new DBHelper(this);
        edtName = findViewById(R.id.admin_add_edtNameAudio);
        edtFilePath= findViewById(R.id.admin_add_edtFileAudio);
        addButton = findViewById(R.id.button_add_audio);
        List<Audio> audioList = dbHelper.getAllAudios();
        audioAdapter = new AudioAdapter(audioList);
        selectButton = findViewById(R.id.btn_ChoiceAudioFile);
        pickAudioLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            selectedAudioFilePath = result.toString();
                            Toast.makeText(AddAudioActivity.this, "Đã chọn file âm thanh!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        selectButton.setOnClickListener(v -> pickAudioFile());
        addButton.setOnClickListener(v -> saveAudioToDatabase());
    }

    private void pickAudioFile() {
        pickAudioLauncher.launch("audio/*");
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_AUDIO_REQUEST_CODE && data != null) {
                Uri audioUri = data.getData();
                if (audioUri != null) {
                    selectedAudioFilePath = audioUri.toString();
                    edtFilePath.setText(selectedAudioFilePath); // Thêm dòng này để hiển thị đường dẫn trên giao diện
                    Toast.makeText(this, "Đã chọn file âm thanh!", Toast.LENGTH_SHORT).show();

                    // Cập nhật danh sách âm thanh hiển thị lên RecyclerView
                    List<Audio> audioList = dbHelper.getAllAudios();
                    audioAdapter.setAudioList(audioList);
                }
            }
        }
    }
    private void saveAudioToDatabase() {
        String audioName = edtName.getText().toString().trim();
        String audioId = UUID.randomUUID().toString();


        if (!audioName.isEmpty() && selectedAudioFilePath != null && !selectedAudioFilePath.isEmpty()) {
            // Tạo đối tượng Audio và lưu vào cơ sở dữ liệu
            Audio audio = new Audio(audioId,audioName, selectedAudioFilePath);
            boolean isSuccess = dbHelper.insertAudio(audio);

            if (isSuccess) {
                Toast.makeText(this, "Đã lưu thông tin âm thanh vào cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                // Cập nhật danh sách âm thanh hiển thị lên RecyclerView
                List<Audio> audioList = dbHelper.getAllAudios();
                audioAdapter.setAudioList(audioList);
            } else {
                Toast.makeText(this, "Không thể lưu thông tin âm thanh vào cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Vui lòng nhập tên âm thanh và chọn file trước khi lưu", Toast.LENGTH_SHORT).show();
        }
    }



}