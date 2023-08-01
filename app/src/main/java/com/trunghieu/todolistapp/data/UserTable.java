package com.trunghieu.todolistapp.data;

public class UserTable {
    public static final String TABLE_NAME = "User";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PASSWORD + " TEXT, " +
            COLUMN_ROLE  + " INTEGER," +
            "FOREIGN KEY (" + COLUMN_ROLE  + ") REFERENCES " +
            RoleTable.TABLE_NAME + "(" +  RoleTable.COLUMN_ID + "))";

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final  String SELECT_TABLE_QUERY ="SELECT * FROM "+ TABLE_NAME +" ORDER BY "+
            COLUMN_ID  ;
}
