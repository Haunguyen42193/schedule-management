package com.trunghieu.todolistapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "Categories")
public class Category {
    @PrimaryKey
    private String id;
    private String name;
    private String taskId;

    public Category(String name, String taskId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
