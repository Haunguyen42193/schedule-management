package com.trunghieu.todolistapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

@Entity(tableName = "Audio")
public class Audio implements Serializable {
    @PrimaryKey
    private String id;
    private String name;
    private String audioFilePath ;

    //private String taskId;

    public Audio(String id, String name, String audioData) {
        this.id = id;
        this.name = name;
        this.audioFilePath  = audioData;
    }
    public Audio(String name, String audioFilePath) {
        this.id = "AUDIO"+UUID.randomUUID().toString();
        this.name = name;
        this.audioFilePath = audioFilePath;
//        this.taskId = taskId;
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
//    public String getTaskId() {
//        return taskId;
//    }
//    public void setTaskId(String taskId) {this.taskId = taskId;}
    private String generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000; // Tạo số ngẫu nhiên từ 1000 đến 9999
        return String.valueOf(randomNumber);
    }
}
