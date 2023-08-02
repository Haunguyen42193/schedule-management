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

import com.trunghieu.todolistapp.DetailCategoryActivity;
import com.trunghieu.todolistapp.R;
import com.trunghieu.todolistapp.data.CategoryTable;
import com.trunghieu.todolistapp.data.DBHelper;
import com.trunghieu.todolistapp.data.UserTable;
import com.trunghieu.todolistapp.model.Category;
import com.trunghieu.todolistapp.model.User;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Category> arrayList;
    private final SQLiteDatabase database;
    public CategoryAdapter(Context context, ArrayList<Category> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
       Category category = arrayList.get(position);
       holder.txtId.setText(category.getId());
       holder.txtName.setText(category.getName());
       holder.txtDescription.setText(category.getdescription());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Cursor cursor = database.query(
                       CategoryTable.TABLE_NAME,
                       new String[]{CategoryTable.COLUMN_ID, CategoryTable.COLUMN_NAME, CategoryTable.COLUMN_DESCRIPTION},
                       CategoryTable.COLUMN_ID + " = ?",
                       new String[]{category.getId()},
                       null,
                       null,
                       null
               );
               if(cursor !=null && cursor.moveToFirst()){
                   int idIndex = cursor.getColumnIndex(CategoryTable.COLUMN_ID);
                   int nameIndex = cursor.getColumnIndex(CategoryTable.COLUMN_NAME);
                   int descIndex = cursor.getColumnIndex(CategoryTable.COLUMN_DESCRIPTION);

                   if( idIndex !=-1 && nameIndex !=-1 && descIndex != -1){
                       String id = cursor.getString(idIndex);
                       String name = cursor.getString(nameIndex);
                       String desc = cursor.getString(descIndex);

                       Intent intent = new Intent(context, DetailCategoryActivity.class);

                       intent.putExtra("id", id);
                       intent.putExtra("name", name);
                       intent.putExtra("desc", desc);

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
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtId, txtName, txtDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.admin_cateID);
            txtName = itemView.findViewById(R.id.admin_cateName);
            txtDescription = itemView.findViewById(R.id.admin_cateDescription);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateCategoryList(ArrayList<Category> updatedList) {
        arrayList.clear();
        arrayList.addAll(updatedList);
        notifyDataSetChanged();
    }


}
