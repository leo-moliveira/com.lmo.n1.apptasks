package com.lmo.n1.apptasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "AppTasks";

    public Database(Context context ){

        super(context, NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "image TEXT DEFAULT NULL," +
                "completed INTEGER DEFAULT 0)");
        sqLiteDatabase.execSQL("INSERT INTO tasks (title,description) VALUES ('Example Task','This is an example of task')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int currentVersion) {

    }
}
