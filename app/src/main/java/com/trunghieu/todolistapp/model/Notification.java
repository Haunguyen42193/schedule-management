package com.trunghieu.todolistapp.model;

import androidx.room.Entity;

import java.util.UUID;

@Entity(tableName = "Notifications")
public class Notification {
    private String id;
    private String content;
    private String taskId;

    public Notification(String content, String taskId) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.taskId = taskId;
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
}
