package com.trunghieu.todolistapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "Tasks")
public class Task {
    @PrimaryKey
    private String id;
    private String title;
    private String description;
    private String createDate;
    private String completed;
    private int userId;

    public Task(String title, String description, String createDate, String completed, int userId) {
        this.id = UUID.randomUUID().toString();

        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.completed = completed;
        this.userId = userId;
    }

    public Task(String id, String title, String description, String createDate, String completed, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.completed = completed;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getCompleted() {
        return completed;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
