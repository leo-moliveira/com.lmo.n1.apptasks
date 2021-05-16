package com.lmo.n1.apptasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO{
    public static void insert(Task task, Context context){
        ContentValues values = new ContentValues();
        values.put("title",task.getTitle());
        values.put("description",task.getDescription());
        values.put("image",task.getImage());
        values.put("completed",task.getCompleted());

        Database localdb = new Database(context);
        SQLiteDatabase db = localdb.getWritableDatabase();
        db.insert("tasks",null,values);
    }

    public static void edit (Task task, Context context){
        ContentValues values = new ContentValues();
        values.put("title",task.getTitle());
        values.put("description",task.getDescription());
        values.put("image",task.getImage());
        values.put("completed",task.getCompleted());

        Database localdb = new Database(context);
        SQLiteDatabase db = localdb.getWritableDatabase();
        db.update("tasks",values,"id = " + task.getId(),null);
    }

    public static void delete(int id, Context context){
        Database localdb = new Database(context);
        SQLiteDatabase db = localdb.getWritableDatabase();
        db.delete("tasks","id = " + id,null);
    }

    public static List<Task> getListOfTasks(Context context){
        List<Task> list = new ArrayList<>();

        Database localdb = new Database(context);
        SQLiteDatabase db = localdb.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tasks ORDER BY completed", null);
        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex("id")));
                task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                task.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                task.setImage(cursor.getString(cursor.getColumnIndex("image")));
                task.setCompleted(cursor.getInt(cursor.getColumnIndex("completed")) == 1);
                list.add(task);
            }while (cursor.moveToNext());
        }else{
            //nothing
        }
        return list;
    }

    public static Task getTaskById(int id, Context context){
        Database localdb = new Database(context);
        SQLiteDatabase db = localdb.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks WHERE id = " + id, null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex("id")));
            task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            task.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            task.setImage(cursor.getString(cursor.getColumnIndex("image")));
            task.setCompleted(cursor.getInt(cursor.getColumnIndex("completed")) == 1);
            return task;
        }else{
            return null;
        }
    }
    public static void complete(Task task,Context context){
        int taskStatus;
        ContentValues values = new ContentValues();
        Database localdb = new Database(context);
        SQLiteDatabase db = localdb.getWritableDatabase();
        if(task.getCompleted()){
            taskStatus = 0;
        }else{
            taskStatus = 1;
        }
        values.put("completed",taskStatus);

        db.update("tasks",values,"id = " + task.getId(),null);
    }
}
