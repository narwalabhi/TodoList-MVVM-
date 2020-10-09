package com.example.todolistmvvm.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {TodoItem.class}, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {
    private static final String TAG = TodoDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "todo_list";

    private static TodoDatabase myDbInstance;

    public static TodoDatabase getInstance(Context context){
        if (myDbInstance == null){
            synchronized (LOCK){
                Log.d(TAG, "getInstance: new instance");
                myDbInstance = Room.databaseBuilder(context.getApplicationContext(),
                        TodoDatabase.class,
                        TodoDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return myDbInstance;
    }

    public abstract TodoDao dao();

}
