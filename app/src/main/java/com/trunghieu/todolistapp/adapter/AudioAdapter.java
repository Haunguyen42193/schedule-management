package com.trunghieu.todolistapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trunghieu.todolistapp.DetailAudioActivity;
import com.trunghieu.todolistapp.R;
import com.trunghieu.todolistapp.data.AudioTable;
import com.trunghieu.todolistapp.data.CategoryTable;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.model.Audio;

import java.util.ArrayList;
import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    private  ArrayList<Audio> arrayList;
    private final SQLiteDatabase database;
    private final Context context;

    public AudioAdapter( Context context,ArrayList<Audio> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        DBHelper dbHelper= new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item, parent, false);
        return new AudioViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        Audio audio = arrayList.get(position);
        holder.bind(audio);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.query(
                        AudioTable.TABLE_NAME,
                        new String[]{AudioTable.COLUMN_ID, AudioTable.COLUMN_NAME, AudioTable.COLUMN_FILE_PATH, AudioTable.COLUMN_TASK_ID},
                        AudioTable.COLUMN_ID + " = ?",
                        new String[]{audio.getId()},
                        null,
                        null,
                        null
                );
                if(cursor !=null && cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(AudioTable.COLUMN_ID);
                    int nameIndex = cursor.getColumnIndex(AudioTable.COLUMN_NAME);
                    int fileIndex = cursor.getColumnIndex(AudioTable.COLUMN_FILE_PATH);
                    int taskIdIndex = cursor.getColumnIndex(AudioTable.COLUMN_TASK_ID);
                    if(idIndex !=-1 && nameIndex!=-1 && fileIndex !=-1 && taskIdIndex !=-1){
                        String id = cursor.getString(idIndex);
                        String name = cursor.getString(nameIndex);
                        String filePath = cursor.getString(fileIndex);
                        String taskId = cursor.getString(taskIdIndex);
                        Intent intent = new Intent(context, DetailAudioActivity.class);

                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("filePath", filePath);
                        intent.putExtra("taskId", taskId);
                        context.startActivity(intent);
                        cursor.close();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

   public static class AudioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAudioName, textViewAudioID,textViewAudioFilePath;



       public AudioViewHolder(@NonNull View itemView) {
           super(itemView);
           textViewAudioID = itemView.findViewById(R.id.admin_audioID);
           textViewAudioName = itemView.findViewById(R.id.admin_audioName);
           textViewAudioFilePath = itemView.findViewById(R.id.admin_audioDescription);
       }
       public void bind(Audio audio) {
           textViewAudioName.setText(audio.getName());
           textViewAudioFilePath.setText(audio.getAudioFilePath());
           textViewAudioID.setText(audio.getId());
       }
   }
    @SuppressLint("NotifyDataSetChanged")
    public void updateAudioList(ArrayList<Audio> audioList){
        arrayList.clear();
        arrayList.addAll(audioList);
        notifyDataSetChanged();
    }
}
