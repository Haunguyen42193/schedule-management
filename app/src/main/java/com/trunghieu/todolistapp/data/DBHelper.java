package com.trunghieu.todolistapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.trunghieu.todolistapp.model.Audio;
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.Notification;
import com.trunghieu.todolistapp.model.Task;
import com.trunghieu.todolistapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;


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
        db.execSQL(AudioTable.CREATE_QUERY);
        insertRoles(db);
        initData(db);
        isDatabaseCreated = true;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserTable.DROP_TABLE_QUERY);
        db.execSQL(RoleTable.DROP_TABLE_QUERY);
        db.execSQL(TaskTable.DROP_TABLE_TASK);
        db.execSQL(CategoryTable.DROP_TABLE_CATEGORIES);
        db.execSQL(NotificationTable.DROP_TABLE_NOTIFICATION);
        db.execSQL(AudioTable.DROP_QUERY);
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
        values.put(TaskTable.COLUMN_START, t.getStartTime());
        values.put(TaskTable.COLUMN_COMPLETE, t.getCompleted());
        values.put(TaskTable.COLUMN_USER, t.getUserId());
        values.put(TaskTable.COLUMN_CATEGORY,t.getCategoryID());
        values.put(TaskTable.COLUMN_AUDIO,t.getAudioID());
        long rs = db.insert(TaskTable.TABLE_TASKS, null, values);
        db.close();
        return rs != -1;
    }

    public ArrayList<Task> getTaskData(String mail) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TaskTable.TABLE_TASKS +
                " WHERE " + TaskTable.COLUMN_USER + " IN (" +
                "SELECT " + UserTable.COLUMN_ID + " FROM " + UserTable.TABLE_NAME +
                " WHERE " + UserTable.COLUMN_EMAIL + " = ?)";
        Cursor cursor = db.rawQuery(query,new String[] {mail});
        ArrayList<Task> listTask = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String start = cursor.getString(3);
            String complete = cursor.getString(4);
            int userId = cursor.getInt(5);
            String categoryId = cursor.getString(6);
            String audioID = cursor.getString(7);
            Task task = new Task(id, title, description, start, complete, userId, categoryId, audioID);
            listTask.add(task);
        }
        db.close();
        return listTask;
    }

    public Task getTaskById(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TaskTable.TABLE_TASKS +
                " WHERE " + TaskTable.COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query,new String[] {id});
        Task t = null;
        while(cursor.moveToNext()) {
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String start = cursor.getString(3);
            String complete = cursor.getString(4);
            int userId = cursor.getInt(5);
            String categoryId = cursor.getString(6);
            String audioID = cursor.getString(7);
            t = new Task(id, title, description, start, complete, userId, categoryId, audioID);
        }
        db.close();
        return t;
    }


    public ArrayList<Task> getTaskByCategoryIdUserId(String cateId, int uId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TaskTable.TABLE_TASKS,
                null,
                TaskTable.COLUMN_CATEGORY + " = ? and " + TaskTable.COLUMN_USER + " = ?",
                new String[] {cateId, String.valueOf(uId)},
                null,
                null,
                null);
        ArrayList<Task> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String start = cursor.getString(3);
            String complete = cursor.getString(4);
            int userId = cursor.getInt(5);
            String categoryId = cursor.getString(6);
            String audioID = cursor.getString(7);
            Task task = new Task(id, title, description, start, complete, userId, categoryId, audioID);
            list.add(task);
        }
        db.close();
        return list;
    }

    public boolean updateTask(Task t) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_ID, t.getId());
        values.put(TaskTable.COLUMN_TASK_TITLE, t.getTitle());
        values.put(TaskTable.COLUMN_DESCRIPTION, t.getDescription());
        values.put(TaskTable.COLUMN_START, t.getStartTime());
        values.put(TaskTable.COLUMN_COMPLETE, t.getCompleted());
        values.put(TaskTable.COLUMN_USER, t.getUserId());
        values.put(TaskTable.COLUMN_CATEGORY, t.getCategoryID());
        values.put(TaskTable.COLUMN_AUDIO, t.getAudioID());
        String whereClause = TaskTable.COLUMN_ID + " = ?";
        long rs = db.update(TaskTable.TABLE_TASKS,
                values,
                whereClause,
                new String[] {t.getId()}
        );
        db.close();
        return rs > 0;
    }
    public boolean deleteTask(String id) {
        SQLiteDatabase db = getWritableDatabase();
        long rs = 0;
        if (id != null) {
            rs = db.delete(TaskTable.TABLE_TASKS, TaskTable.COLUMN_ID + " = ? ", new String[] {id});
        }
        db.close();
        return rs > 0;
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
    public ArrayList<Category> getCategoryData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(CategoryTable.SELECT_CATEGORIES, null);
        ArrayList<Category> lisCategory = new ArrayList<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            Category category = new Category(id, name, description);
            lisCategory.add(category);
        }
        return lisCategory;
    }
    public Category getCategoryByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Category category = null;
        Cursor cursor = db.query(
                CategoryTable.TABLE_NAME,
                null,
                CategoryTable.COLUMN_ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(CategoryTable.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(CategoryTable.COLUMN_NAME);
            int descIndex = cursor.getColumnIndex(CategoryTable.COLUMN_DESCRIPTION);


            if (idIndex >= 0 && nameIndex >= 0 && descIndex >= 0 ) {
                String idCate = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String description = cursor.getString(descIndex);

                category = new Category(idCate, name, description);

            }
            cursor.close();
        }

        return category;
    }
    public boolean deleteCategoryByID(String id) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(CategoryTable.TABLE_NAME, CategoryTable.COLUMN_ID + " = ?", new String[]{id});
        db.close();
        return result > 0;
    }
    public boolean updateCategoryByID(String id, Category updateCategory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CategoryTable.COLUMN_NAME, updateCategory.getName());
        values.put(CategoryTable.COLUMN_DESCRIPTION, updateCategory.getdescription());


        int rowsAffected = db.update(
                CategoryTable.TABLE_NAME,
                values,
                CategoryTable.COLUMN_ID + " = ?",
                new String[]{id}
        );
        db.close();
        return rowsAffected > 0;
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

    public boolean insertAudio(Audio t) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(AudioTable.COLUMN_ID, t.getId());
        values.put(AudioTable.COLUMN_NAME, t.getName());
        values.put(AudioTable.COLUMN_FILE_PATH, t.getAudioFilePath());
        long rs = db.insert(AudioTable.TABLE_NAME, null, values);
        db.close();
        return rs != -1 ;
    }

    public ArrayList<Audio> getAudioData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(AudioTable.SELECT_QUERY,null);
        ArrayList<Audio> listAudio = new ArrayList<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String filePath = cursor.getString(2);
            Audio audio= new Audio(id, name, filePath);
            listAudio.add(audio);
        }
        return listAudio;
    }
    public Audio getAudioByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Audio audio = null;
        Cursor cursor = db.query(
                AudioTable.TABLE_NAME,
                null,
                AudioTable.COLUMN_ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(AudioTable.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(AudioTable.COLUMN_NAME);
            int filePathIndex = cursor.getColumnIndex(AudioTable.COLUMN_FILE_PATH);

            if (idIndex >= 0 && nameIndex >= 0 && filePathIndex >= 0) {
                String idAudio = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String filePath = cursor.getString(filePathIndex);

                audio = new Audio(idAudio, name, filePath);
            }
            cursor.close();
        }

        return audio;
    }

    public boolean deleteAudioByID(String id) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(AudioTable.TABLE_NAME, AudioTable.COLUMN_ID + " = ?", new String[]{id});
        db.close();
        return result > 0;
    }
    public boolean updateAudioByID(String id, Audio updateAudio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AudioTable.COLUMN_NAME, updateAudio.getName());
        values.put(AudioTable.COLUMN_FILE_PATH, updateAudio.getAudioFilePath());


        int rowsAffected = db.update(
                AudioTable.TABLE_NAME,
                values,
                AudioTable.COLUMN_ID + " = ?",
                new String[]{id}
        );
        db.close();
        return rowsAffected > 0;
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

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                UserTable.TABLE_NAME,
                null,
                UserTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(UserTable.COLUMN_NAME);
            int emailIndex = cursor.getColumnIndex(UserTable.COLUMN_EMAIL);
            int passwordIndex = cursor.getColumnIndex(UserTable.COLUMN_PASSWORD);
            int roleIndex = cursor.getColumnIndex(UserTable.COLUMN_ROLE);
            String name = cursor.getString(nameIndex);
            String userMail = cursor.getString(emailIndex);
            String password = cursor.getString(passwordIndex);
            String role = cursor.getString(roleIndex);
            User user = new User(id, name, userMail, password, Integer.parseInt(role));
            cursor.close();
            db.close();
            return user;
        }
        return null;
    }

    public Category getCategoryById(String id) {
        Category c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        if (id != null) {
            Cursor cursor = db.query(CategoryTable.TABLE_NAME,
                    null,
                    CategoryTable.COLUMN_ID + " = ?",
                    new String[] {id},
                    null,
                    null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                String cateId = id;
                int nameIndex = cursor.getColumnIndex(CategoryTable.COLUMN_NAME);
                int desIndex = cursor.getColumnIndex(CategoryTable.COLUMN_DESCRIPTION);
                String name = cursor.getString(nameIndex);
                String des = cursor.getString(desIndex);
                c = new Category(cateId, name, des);
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        db.close();
        return c;

    }
    public ArrayList<Category> getCategoriesData() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Category> list = new ArrayList<>();
        Cursor cursor = db.query(CategoryTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
             do {
                int cateIdIndex = cursor.getColumnIndex(CategoryTable.COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(CategoryTable.COLUMN_NAME);
                int desIndex = cursor.getColumnIndex(CategoryTable.COLUMN_DESCRIPTION);
                String name = cursor.getString(nameIndex);
                String des = cursor.getString(desIndex);
                String id = cursor.getString(cateIdIndex);
                Category c = new Category(id, name, des);
                list.add(c);
            } while (cursor.moveToNext());
        }
        if(cursor != null) {
            cursor.close();
        }
        db.close();
        return list;
    }
    public boolean checkTitleExists(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            // Query the database to check if the title exists
            String query = "SELECT * FROM " + TaskTable.TABLE_TASKS + " WHERE " + TaskTable.COLUMN_TASK_TITLE + " = ?";
            cursor = db.rawQuery(query, new String[]{title});

            // If the cursor has any rows, it means the title exists
            if (cursor != null && cursor.getCount() > 0) {
                exists = true;
            }
        } finally {
            // Close the cursor and database connection
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return exists;
    }
    public void insertUser(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_NAME, user.getName());
        values.put(UserTable.COLUMN_EMAIL, user.getEmail());
        values.put(UserTable.COLUMN_PASSWORD, user.getPassword());
        values.put(UserTable.COLUMN_ROLE, user.getRole());
        db.insert(UserTable.TABLE_NAME, null, values);


    }
    public void insertCategory(SQLiteDatabase db, Category t) {
        ContentValues values = new ContentValues();
        values.put(CategoryTable.COLUMN_ID, t.getId());
        values.put(CategoryTable.COLUMN_NAME, t.getName());
        values.put(CategoryTable.COLUMN_DESCRIPTION, t.getdescription());
        db.insert(CategoryTable.TABLE_NAME, null, values);


    }
    public void insertAudio(SQLiteDatabase db, Audio t) {

        ContentValues values= new ContentValues();
        values.put(AudioTable.COLUMN_ID, t.getId());
        values.put(AudioTable.COLUMN_NAME, t.getName());
        values.put(AudioTable.COLUMN_FILE_PATH, t.getAudioFilePath());
         db.insert(AudioTable.TABLE_NAME, null, values);


    }
    private void initData(SQLiteDatabase db) {
        if (!isDatabaseCreated) {
            insertUser(db, new User("Hieu", "hieu@gmail.com", "123456", 1)); // Sử dụng tham số db để chèn dữ liệu user
            insertUser(db, new User("Hau", "hau@gmail.com", "123456", 1));
            insertUser(db, new User("Quan", "quan@gmail.com", "123456", 1));
            insertCategory(db, new Category("Work", "Some things to do in your work"));
            insertCategory(db, new Category("Study", "Some things to do like exercise"));
            insertCategory(db, new Category("Play", "Some things to play like video game"));
            insertCategory(db, new Category("Eating", "Some things to do"));
            insertCategory(db, new Category("Sleep", "Some things to "));
            insertAudio(db, new Audio("Music","/res/raw/audio.mp3"));
            insertAudio(db, new Audio("Default","/res/raw/audiodefaul.mp3"));
        }
    }


}
