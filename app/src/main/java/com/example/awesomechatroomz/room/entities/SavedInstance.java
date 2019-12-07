package com.example.awesomechatroomz.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.annotations.NotNull;

@Entity(tableName = "saved_instances")
public class SavedInstance {
    @PrimaryKey
    private int id;

    private String name;
    private String databaseId;

    public SavedInstance() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }
}
