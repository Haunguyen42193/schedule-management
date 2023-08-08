package com.trunghieu.todolistapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "Categories")
public class Category implements Serializable {
    @PrimaryKey
    private String id;
    private String name;
    private String description;

    public Category(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Category(String name, String description) {
        this.id ="CATE"+ UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

    public Category(String id, String name, String description) {
        this.id = id;
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
