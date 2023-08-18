package com.trunghieu.todolistapp.data;

import static com.trunghieu.todolistapp.data.TaskTable.TABLE_TASKS;

public class AudioTable {
    public static final String TABLE_NAME = "AUDIO";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_FILE_PATH = "FilePath"; // Change the column name to FilePath
    public static final String COLUMN_TASK_ID = "taskId";

    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " TEXT PRIMARY KEY, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_FILE_PATH + " TEXT"
           +COLUMN_TASK_ID + " TEXT NOT NULL, " +
            "FOREIGN KEY ( " + COLUMN_TASK_ID + " ) REFERENCES "  +
            TABLE_TASKS + " ( " + TaskTable.COLUMN_ID + " ))";
    public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;
}

