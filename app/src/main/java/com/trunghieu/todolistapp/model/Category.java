package com.trunghieu.todolistapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "Categories")
public class Category {
    @PrimaryKey
    private String id;
    private String name;
    private String description;

    public Category(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getdescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setdescription(String taskId) {
        this.description = taskId;
    }
}
