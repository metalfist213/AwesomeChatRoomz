package com.example.awesomechatroomz.room;

import android.app.Activity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.awesomechatroomz.room.dao.SavedInstanceDao;
import com.example.awesomechatroomz.room.entities.SavedInstance;

@Database(entities = {SavedInstance.class}, version = 1)
public abstract class SavedInstancesDatabase extends RoomDatabase {
    private static SavedInstancesDatabase instance;
    public abstract SavedInstanceDao savedInstanceDao();

    public static SavedInstancesDatabase getInstance(Activity activity) {
        if(instance == null) {
            instance = Room.databaseBuilder(activity.getApplicationContext(), SavedInstancesDatabase.class,
            "saved-instances-database").build();
        }
        return instance;
    }
}
