package com.trunghieu.todolistapp.data;

import static com.trunghieu.todolistapp.data.TaskTable.TABLE_TASKS;

public class CategoryTable {
    public static final String TABLE_NAME = "Categories";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION ="description";

    public static String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " ( " + COLUMN_ID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_DESCRIPTION + " TEXT)";


    public static String DROP_TABLE_CATEGORIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
