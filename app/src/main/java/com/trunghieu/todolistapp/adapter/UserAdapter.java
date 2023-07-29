package com.trunghieu.todolistapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trunghieu.todolistapp.DetailUserActivity;
import com.trunghieu.todolistapp.R;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.data.UserTable;
import com.trunghieu.todolistapp.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<User> arrayList;
    private final SQLiteDatabase database;
    public UserAdapter(Context context, ArrayList<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = arrayList.get(position);
        holder.tdtID.setText(String.valueOf(user.getId()));
        holder.tdtEmail.setText(user.getEmail());
        holder.tdtName.setText(user.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.query(UserTable.TABLE_NAME,
                        new String[]{UserTable.COLUMN_NAME, UserTable.COLUMN_EMAIL,
                                UserTable.COLUMN_PASSWORD, UserTable.COLUMN_ROLE},
                        UserTable.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(user.getId())},
                        null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(UserTable.COLUMN_NAME);
                    int emailIndex = cursor.getColumnIndex(UserTable.COLUMN_EMAIL);
                    int passwordIndex = cursor.getColumnIndex(UserTable.COLUMN_PASSWORD);
                    int roleIndex = cursor.getColumnIndex(UserTable.COLUMN_ROLE);

                    if (nameIndex != -1 && emailIndex != -1 && passwordIndex != -1 && roleIndex != -1) {
                        String name = cursor.getString(nameIndex);
                        String email = cursor.getString(emailIndex);
                        String password = cursor.getString(passwordIndex);
                        String role = cursor.getString(roleIndex);

                        Intent intent = new Intent(context, DetailUserActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        intent.putExtra("role", role);
                        context.startActivity(intent);

                        cursor.close();
                    }
                }

            }

        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tdtID, tdtName, tdtEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            tdtID = itemView.findViewById(R.id.admin_userId);
            tdtName = itemView.findViewById(R.id.admin_userName);
            tdtEmail = itemView.findViewById(R.id.admin_userEmail);
        }
    }
    public void updateUser(User updatedUser) {
        // Find the position of the updated user in the dataset
        int position = arrayList.indexOf(updatedUser);
        if (position != -1) {
            // Update the user in the dataset
            arrayList.set(position, updatedUser);
            // Notify the adapter about the change in the dataset
            notifyItemChanged(position);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateUserList(ArrayList<User> updatedList) {
        arrayList.clear();
        arrayList.addAll(updatedList);
        notifyDataSetChanged();
    }
}
