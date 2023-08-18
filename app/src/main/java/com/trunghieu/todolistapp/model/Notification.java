package com.trunghieu.todolistapp.model;

import androidx.room.Entity;

import java.util.UUID;

@Entity(tableName = "Notifications")
public class Notification {
    private String id;
    private String content;
    private String taskId;

    private String filePath;

    public Notification(String content, String taskId, String filePath) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.taskId = taskId;
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFilePath() {return filePath;
    }
    public void setFilePath(String filePath) {this.filePath = filePath;
    }
}
