package com.example.awesomechatroomz.domain;

import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.awesomechatroomz.interfaces.AsyncTaskCallback;
import com.example.awesomechatroomz.models.LoggedInUser;
import com.example.awesomechatroomz.models.User;
import com.example.awesomechatroomz.room.SavedInstancesDatabase;
import com.example.awesomechatroomz.room.entities.SavedInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

/**
 * Manages the login of the user.
 * Can also register.
 */
public class LoginManager {

    private DatabaseReference database;
    private ImageManager manager;
    private SavedInstancesDatabase localDatabase;

    private LoggedInUser loggedInUser;

    @Inject
    public LoginManager(DatabaseReference database, ImageManager manager, SavedInstancesDatabase localDatabase, LoggedInUser loggedInUser) {
        this.database = database;
        this.manager = manager;
        this.localDatabase = localDatabase;
        this.loggedInUser = loggedInUser;
    }

    /**
     * Attempts to login using a saved model of the user.
     * @param callback, Callback on results.
     * Passes a user back, if found.
     */
    public void AttemptAutoLogin(final LoginCallback callback) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                List<SavedInstance> instancesSaved = localDatabase.savedInstanceDao().getSavedInstances();

                if(instancesSaved.size()>0) {
                    SavedInstance i = instancesSaved.get(0);
                    User user = new User();
                    user.setId(i.getDatabaseId());
                    user.setName(i.getName());
                    loggedInUser.setUser(user);

                    callback.OnFinished(user);
                } else {
                    callback.OnFinished(null);
                }
            }
        });
    }

    public interface LoginCallback{
        void OnFinished(User user);
    }


    public void updateUserInformation(final User user, final AsyncTaskCallback onStart) {
        new UpdateUserInformationTask(onStart, manager, database).execute(user);
        saveInLocalDatabase(user);
    }

    private void saveInLocalDatabase(final User user) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                SavedInstance i = new SavedInstance();
                i.setDatabaseId(user.getId());
                i.setName(i.getName());

                localDatabase.savedInstanceDao().deleteAll();
                localDatabase.savedInstanceDao().insert(i);
            }
        });
    }


    private static class UpdateUserInformationTask extends AsyncTask<User, User, User> {

        private AsyncTaskCallback callback;
        private ImageManager manager;
        private DatabaseReference database;

        UpdateUserInformationTask(AsyncTaskCallback callback, ImageManager manager, DatabaseReference database) {
            this.callback = callback;
            this.manager = manager;
            this.database = database;
        }

        @Override
        protected User doInBackground(final User... users) {
            final User user = users[0];
            Task<Uri> t = null;

            try {
                t = manager.uploadFile("avatars/"+user.getId(), Uri.parse(user.getAvatarURL())).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        //user.setAvatarURL(task.getResult().toString());
                        database.child("users").child(user.getId()).setValue(user);
                    }


                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            while( !t.isComplete());


            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            callback.onPostExecute();
        }

        @Override
        protected void onPreExecute() {
            callback.OnPreExecute();
        }
    }
}
