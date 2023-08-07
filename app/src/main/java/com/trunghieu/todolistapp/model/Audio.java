package com.trunghieu.todolistapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "Audio")
public class Audio implements Serializable {
    @PrimaryKey
    private String id;
    private String name;
    private String audioFilePath ;

    public Audio(String id, String name, String audioData) {
        this.id = id;
        this.name = name;
        this.audioFilePath  = audioData;
    }

    public Audio(String name, String audioFilePath) {
        this.name = name;
        this.audioFilePath = audioFilePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath  = audioFilePath;
    }
}
