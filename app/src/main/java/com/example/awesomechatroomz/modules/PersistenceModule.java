package com.example.awesomechatroomz.modules;


import androidx.room.Room;

import com.example.awesomechatroomz.room.SavedInstancesDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    @Provides
    public DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides
    public StorageReference provideStorageReference() {
        return FirebaseStorage.getInstance().getReference();
    }
}
