package com.trunghieu.todolistapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.Notification;
import com.trunghieu.todolistapp.model.Task;
import com.trunghieu.todolistapp.model.User;

import java.util.ArrayList;

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
        db.execSQL(TaskTable.CREATE_TABLE_TASK);
        db.execSQL(CategoryTable.CREATE_TABLE_CATEGORIES);
        db.execSQL(NotificationTable.CREATE_TABLE_NOTIFICATION);
        insertRoles(db);
        isDatabaseCreated = true;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserTable.DROP_TABLE_QUERY);
        db.execSQL(RoleTable.DROP_TABLE_QUERY);
        db.execSQL(TaskTable.DROP_TABLE_TASK);
        db.execSQL(CategoryTable.DROP_TABLE_CATEGORIES);
        db.execSQL(NotificationTable.DROP_TABLE_NOTIFICATION);
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
    public boolean insertUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_NAME, user.getName());
        values.put(UserTable.COLUMN_EMAIL, user.getEmail());
        values.put(UserTable.COLUMN_PASSWORD, user.getPassword());
        values.put(UserTable.COLUMN_ROLE, user.getRole());
        long result = db.insert(UserTable.TABLE_NAME, null, values);
        db.close();
        return result != -1;

    }
    public ArrayList<User> getUserData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery(UserTable.SELECT_TABLE_QUERY, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            User user = new User(id, name, email);
            arrayList.add(user);
        }
        return arrayList;
    }

    public boolean insertTask(Task t) {
        SQLiteDatabase db = getWritableDatabase();
        //Chuẩn bị dữ liệu để thêm vào bảng
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_ID, t.getId());
        values.put(TaskTable.COLUMN_TASK_TITLE, t.getTitle());
        values.put(TaskTable.COLUMN_DESCRIPTION, t.getDescription());
        values.put(TaskTable.COLUMN_CREATED, t.getCreateDate());
        values.put(TaskTable.COLUMN_COMPLETE, t.getCompleted());
        values.put(TaskTable.COLUMN_USER, t.getUserId());
        values.put(TaskTable.COLUMN_CATEGORY,t.getCategoryID());
        long rs = db.insert(TaskTable.TABLE_TASKS, null, values);
        db.close();
        return rs != -1;
    }

    public ArrayList<Task> getTaskData() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(TaskTable.SELECT_TASK,null);
        ArrayList<Task> listTask = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String create = cursor.getString(3);
            String complete = cursor.getString(4);
            int userId = cursor.getInt(5);
            String categoryId = cursor.getString(6);
            Task task = new Task(id, title, description, create, complete, userId, categoryId);
            listTask.add(task);
        }
        return listTask;
    }
    public boolean insertCategory(Category t) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CategoryTable.COLUMN_ID, t.getId());
        values.put(CategoryTable.COLUMN_NAME, t.getName());
        values.put(CategoryTable.COLUMN_DESCRIPTION, t.getdescription());
        long rs = db.insert(CategoryTable.TABLE_NAME, null, values);
        db.close();
        return rs != -1;
    }


    public boolean insertNotification(Notification t) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotificationTable.COLUMN_ID, t.getId());
        values.put(NotificationTable.COLUMN_CONTENT, t.getContent());
        values.put(NotificationTable.COLUMN_TASK_ID, t.getTaskId());
        long rs = db.insert(NotificationTable.TABLE_NOTIFICATION, null, values);
        db.close();
        return rs != -1;
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

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        Cursor cursor = db.query(
                UserTable.TABLE_NAME,
                null,
                UserTable.COLUMN_EMAIL + " = ?",
                new String[]{email},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(UserTable.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(UserTable.COLUMN_NAME);
            int emailIndex = cursor.getColumnIndex(UserTable.COLUMN_EMAIL);
            int passwordIndex = cursor.getColumnIndex(UserTable.COLUMN_PASSWORD);
            int roleIndex = cursor.getColumnIndex(UserTable.COLUMN_ROLE);

            if (idIndex >= 0 && nameIndex >= 0 && emailIndex >= 0 && passwordIndex >= 0 && roleIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String userMail = cursor.getString(emailIndex);
                String password = cursor.getString(passwordIndex);
                String role = cursor.getString(roleIndex);
                user = new User(id, name, userMail, password, Integer.parseInt(role));
            }
            cursor.close();
        }

        return user;
    }
    public boolean updateUserByEmail(String email, User updatedUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UserTable.COLUMN_NAME, updatedUser.getName());
        values.put(UserTable.COLUMN_EMAIL, updatedUser.getEmail());
        values.put(UserTable.COLUMN_PASSWORD, updatedUser.getPassword());
        values.put(UserTable.COLUMN_ROLE, updatedUser.getRole());

        int rowsAffected = db.update(
                UserTable.TABLE_NAME,
                values,
                UserTable.COLUMN_EMAIL + " = ?",
                new String[]{email}
        );
        db.close();
        return rowsAffected > 0;
    }
    public boolean deleteUserByEmail(String email) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(UserTable.TABLE_NAME, UserTable.COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();
        return result > 0;
    }
}
