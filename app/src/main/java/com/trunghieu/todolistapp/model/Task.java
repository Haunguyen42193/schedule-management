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
    private String startTime;
    private String completed;
    private int userId;
    private String categoryID;

    public Task(String title, String description, String startTime, String completed, int userId, String categoryID) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.completed = completed;
        this.userId = userId;
        this.categoryID = categoryID;
    }

    public Task(String id, String title, String description, String startTime, String completed, int userId, String categoryID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.completed = completed;
        this.userId = userId;
        this.categoryID = categoryID;
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

    public String getStartTime() {
        return startTime;
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

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}
