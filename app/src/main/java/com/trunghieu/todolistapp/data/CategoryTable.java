package com.trunghieu.todolistapp.data;

import static com.trunghieu.todolistapp.data.TaskTable.TABLE_TASKS;

public class CategoryTable {
    public static final String TABLE_CATEGORIES = "Categories";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TASK_ID = "taskId";
    public static String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS " +
            TABLE_CATEGORIES + " ( " + COLUMN_ID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_TASK_ID + " TEXT, " +
            " FOREIGN KEY ( " + COLUMN_TASK_ID + " ) REFERENCES " +
            TABLE_TASKS + " ( " + TaskTable.COLUMN_ID + " ))";
    public static String DROP_TABLE_CATEGORIES = "DROP TABLE IF EXISTS " + TABLE_CATEGORIES;
}
