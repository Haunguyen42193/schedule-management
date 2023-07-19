package com.trunghieu.todolistapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TodoApp.db";
    private static final int DATABASE_VERSION = 1;
    private boolean isDatabaseCreated = false;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.CREATE_TABLE_QUERY);
        db.execSQL(RoleTable.CREATE_TABLE_QUERY);

        insertRoles(db);
        isDatabaseCreated= true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserTable.DROP_TABLE_QUERY);
        db.execSQL(RoleTable.DROP_TABLE_QUERY);
        onCreate(db);

    }
    private void insertRoles(SQLiteDatabase db) {
        if (!isDatabaseCreated) {
            ContentValues values = new ContentValues();
            values.put(RoleTable.COLUMN_ID, 1);
            values.put(RoleTable.COLUMN_NAME, "Admin");
            db.insert(RoleTable.TABLE_NAME, null, values);

            values.clear();
            values.put(RoleTable.COLUMN_ID, 2);
            values.put(RoleTable.COLUMN_NAME, "User");
            db.insert(RoleTable.TABLE_NAME, null, values);
        }
    }


    public boolean insertUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UserTable.COLUMN_NAME, user.getName());
        values.put(UserTable.COLUMN_EMAIL, user.getEmail());
        values.put(UserTable.COLUMN_PASSWORD,user.getPassword());
        values.put(UserTable.COLUMN_ROLE,user.getRole());
        long result = db.insert(UserTable.TABLE_NAME, null, values);
        db.close();
        return result != -1;

    }
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable.COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean emailExists = cursor.getCount() > 0;
        cursor.close();
        return emailExists;
    }
    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable.COLUMN_EMAIL + " = ? AND " + UserTable.COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean emailPasswordMatch = cursor.getCount() > 0;
        cursor.close();
        return emailPasswordMatch;
    }
}
