package com.trunghieu.todolistapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trunghieu.todolistapp.adapter.AudioAdapter;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Audio;
import com.trunghieu.todolistapp.model.Category;

public class DetailAudioActivity extends AppCompatActivity {
    private Button btnDelete, btnUpdate, btnChoiceFile;
    private EditText edtName, edtFilePath;
    DBHelper dbHelper;
    ActivityResultLauncher<String> pickAudioLauncher;
    private String selectedAudioFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_audio);
        edtName = findViewById(R.id.admin_detail_edtNameAudio);
        edtFilePath = findViewById(R.id.admin_detail_edtFileAudio);
        btnDelete = findViewById(R.id.button_delete_audio);
        btnUpdate = findViewById(R.id.button_update_audio);
        btnChoiceFile = findViewById(R.id.btn_ChoiceAudioFile);
        dbHelper = new DBHelper(this);
        String audioId = getIntent().getStringExtra("id");
        Audio currentAudio = dbHelper.getAudioByID(audioId);
        if (currentAudio != null) {
            edtName.setText(currentAudio.getName());
            edtFilePath.setText(currentAudio.getAudioFilePath());
        }
        pickAudioLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            selectedAudioFilePath = result.toString();
                            edtFilePath.setText(selectedAudioFilePath); // Gán đường dẫn tệp đã chọn
                            Toast.makeText(DetailAudioActivity.this, "Đã chọn tệp âm thanh!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        btnChoiceFile.setOnClickListener(v -> pickAudioFile());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClicked(v, currentAudio.getId());

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateClicked(v, audioId);
            }
        });

    }

    public void onDeleteClicked(View view, String id) {
        boolean isDeleted = dbHelper.deleteAudioByID(id);
        if (isDeleted) {
            Toast.makeText(this, "Âm thanh đã được xóa ", Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Toast.makeText(this, "Có lỗi xảy ra khi xóa danh muc.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUpdateClicked(View view, String id) {
        String name = edtName.getText().toString();
        String filePath = edtFilePath.getText().toString();


        Audio updateAudio = new Audio(id, name, filePath);


        boolean isUpdated = dbHelper.updateAudioByID(id, updateAudio);

        if (isUpdated) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updateAudio", updateAudio);// ?? check
            setResult(RESULT_OK, resultIntent);
            finish();
            Toast.makeText(this, "Thông tin âm thanh đã được cập nhật.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Có lỗi xảy ra khi cập nhật thông tin âm thanh.", Toast.LENGTH_SHORT).show();
        }
    }
    private void pickAudioFile() {
        pickAudioLauncher.launch("audio/*");
    }
}