package com.example.awesomechatroomz.interfaces;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.awesomechatroomz.models.User;
import com.google.android.gms.common.api.ApiException;

public interface ILoginMethod {
    void prepare(AppCompatActivity activity);
    User onActivityResult(int requestCode, int resultCode, Intent data) throws ApiException;
}
