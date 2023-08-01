package com.trunghieu.todolistapp.data;

import static com.trunghieu.todolistapp.data.UserTable.TABLE_NAME;

public class TaskTable {
    public static final String TABLE_TASKS = "Tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CREATED = "createdTask";
    public static final String COLUMN_COMPLETE = "completed";
    public static final String COLUMN_USER = "userId";

    public static final  String COLUMN_CATEGORY ="categoryId";
    public static String CREATE_TABLE_TASK =
            "CREATE TABLE " + TABLE_TASKS + " ( "
            + COLUMN_ID + " TEXT PRIMARY KEY, "
            + COLUMN_TASK_TITLE + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_CREATED + " TEXT NOT NULL, "
            + COLUMN_COMPLETE + " TEXT, "
            + COLUMN_USER + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_USER + ") REFERENCES "
            + TABLE_NAME + "(" + RoleTable.COLUMN_ID +"), "
            + "FOREIGN KEY (" + COLUMN_CATEGORY + ") REFERENCES "
            + CategoryTable.TABLE_NAME + "(" + RoleTable.COLUMN_ID +"))";

    public static String DROP_TABLE_TASK =
            "DROP TABLE IF EXISTS " + TABLE_TASKS;
    public static String SELECT_TASK = "SELECT * FROM " + TABLE_TASKS;
}
