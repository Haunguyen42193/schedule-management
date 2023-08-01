package com.trunghieu.todolistapp.data;

public class RoleTable {
    public static final String TABLE_NAME = "Role";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NAME ="Name";
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT) " ;
    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
