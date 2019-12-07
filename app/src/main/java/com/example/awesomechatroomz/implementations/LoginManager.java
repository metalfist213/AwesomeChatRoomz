package com.example.awesomechatroomz.implementations;

import android.os.AsyncTask;

import com.example.awesomechatroomz.interfaces.AsyncTaskCallback;
import com.example.awesomechatroomz.models.User;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

public class LoginManager {

    private DatabaseReference database;

    @Inject
    public LoginManager(DatabaseReference database) {
        this.database = database;
    }

    public void updateUserInformation(User user, final AsyncTaskCallback onStart) {

        database.child("users").child(user.getId()).setValue(user);


        AsyncTask<User, User, User> doInBackground = new AsyncTask<User, User, User>() {
            @Override
            protected User doInBackground(User... users) {
                database.child("users").child(users[0].getId()).setValue(users[0]);
                return users[0];
            }

            @Override
            protected void onPreExecute() {
                onStart.OnPreExecute();
            }

            @Override
            protected void onPostExecute(User user) {
                onStart.onPostExecute();
            }
        };
        doInBackground.execute(user);
    }
}
