package com.example.awesomechatroomz.interfaces;

import android.content.Intent;

import com.example.awesomechatroomz.models.User;

public interface ILoginMethod {

    public User onActivityResult(int requestCode, int resultCode, Intent data);
}
