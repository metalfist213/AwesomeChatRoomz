package com.example.awesomechatroomz.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.awesomechatroomz.room.entities.SavedInstance;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SavedInstanceDao {

    @Query("SELECT * FROM saved_instances")
    List<SavedInstance> getSavedInstances();

    @Insert(onConflict = REPLACE)
    void insert(SavedInstance instance);


    @Query("DELETE FROM saved_instances")
    void deleteAll();
}
