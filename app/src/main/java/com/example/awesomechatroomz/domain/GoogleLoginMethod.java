package com.example.awesomechatroomz.domain;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.interfaces.ILoginMethod;
import com.example.awesomechatroomz.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;


public class GoogleLoginMethod implements ILoginMethod {
    private static final String TAG = "GoogleLoginMethod";
    @Inject
    public GoogleLoginMethod() {

    }

    private AppCompatActivity activity;
    @Override
    public void prepare(AppCompatActivity activity) {
        SignInButton signInButton = activity.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        activity.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(v);
            }
        });
        this.activity = activity;
    }

    private void clicked(View v) {
        if (v.getId() == R.id.sign_in_button) {
            signIn();
            // ...
        }
    }

    private void signIn() {

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(activity.getString(R.string.default_google_server)).requestEmail().requestProfile().build();

        GoogleSignInClient client = GoogleSignIn.getClient(activity, options);
        Intent signInIntent = client.getSignInIntent();

        activity.startActivityForResult(signInIntent, 1);
    }

    @Override
    public User onActivityResult(int requestCode, int resultCode, Intent data) throws ApiException {
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            return handleSignInResult(task);
        }
        
        
        return null;
    }

    private User handleSignInResult(Task<GoogleSignInAccount> completedTask) throws ApiException {

        GoogleSignInAccount account = completedTask.getResult(ApiException.class);

        User user = new User();

        user.setName(account.getGivenName());
        user.setId(account.getId());
        user.setAvatarURL(account.getPhotoUrl().toString());

        return user;
    }
}
