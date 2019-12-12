package com.example.awesomechatroomz.interfaces;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.awesomechatroomz.models.User;
import com.google.android.gms.common.api.ApiException;

public interface ILoginMethod {
    /**
     * Prepares the instance for execution.
     * @param activity
     */
    void prepare(AppCompatActivity activity);

    /**
     * Should be called in the activity.
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     * @throws ApiException
     */
    User onActivityResult(int requestCode, int resultCode, Intent data) throws ApiException;
}
