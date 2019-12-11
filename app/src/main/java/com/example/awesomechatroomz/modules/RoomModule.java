package com.example.awesomechatroomz.modules;

import android.app.Application;

import androidx.room.Room;

import com.example.awesomechatroomz.room.SavedInstancesDatabase;
import com.example.awesomechatroomz.room.dao.SavedInstanceDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private SavedInstancesDatabase database;

    public RoomModule(Application application) {
        database = Room.databaseBuilder(application, SavedInstancesDatabase.class, "saved-instances-database").build();
    }

    @Singleton
    @Provides
    public SavedInstancesDatabase providesRoomDatabase() {
        return database;
    }

    @Singleton
    @Provides
    public SavedInstanceDao providesSavedInstancesDao(SavedInstancesDatabase database) {
        return database.savedInstanceDao();
    }
}
