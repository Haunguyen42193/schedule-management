package com.trunghieu.todolistapp.data;

import static com.trunghieu.todolistapp.data.AudioTable.TABLE_NAME;
import static com.trunghieu.todolistapp.data.TaskTable.TABLE_TASKS;

public class NotificationTable {
    public static final String TABLE_NOTIFICATION = "Notifications";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TASK_ID = "taskId";
    public static String CREATE_TABLE_NOTIFICATION =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATION +
            " ( " + COLUMN_ID + " TEXT PRIMARY KEY, " +
            COLUMN_CONTENT + " TEXT NOT NULL, " +
            COLUMN_TASK_ID + " TEXT NOT NULL, " +
            "FOREIGN KEY ( " + COLUMN_TASK_ID + " ) REFERENCES " +
            TABLE_TASKS + " ( " + TaskTable.COLUMN_ID + " ))" ;
    public static String DROP_TABLE_NOTIFICATION = "DROP TABLE IF EXISTS " + TABLE_NOTIFICATION;
}
