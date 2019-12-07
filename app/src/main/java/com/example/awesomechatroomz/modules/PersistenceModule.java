package com.example.awesomechatroomz.modules;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {


    @Provides
    public DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }
}
