package com.trunghieu.todolistapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trunghieu.todolistapp.R;
import com.trunghieu.todolistapp.model.Audio;

import java.util.ArrayList;
import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    private List<Audio> audioList;

    public AudioAdapter(List<Audio> audioList) {
        this.audioList = audioList;
    }

    public void setAudioList(List<Audio> audioList) {
        this.audioList = audioList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        Audio audio = audioList.get(position);
        holder.bind(audio);
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    static class AudioViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewAudioName;
        private TextView textViewAudioFilePath;
        private TextView textViewAudioID;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAudioID= itemView.findViewById(R.id.admin_audioID);
            textViewAudioName = itemView.findViewById(R.id.admin_audioName);
            textViewAudioFilePath = itemView.findViewById(R.id.admin_audioDescription);
        }

        public void bind(Audio audio) {
            textViewAudioName.setText(audio.getName());
            textViewAudioFilePath.setText(audio.getAudioFilePath());
        }
    }
}
