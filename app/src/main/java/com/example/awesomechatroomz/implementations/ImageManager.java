package com.example.awesomechatroomz.implementations;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

public class ImageManager {

    private StorageReference reference;

    @Inject
    public ImageManager(StorageReference reference) {
        this.reference = reference;
    }

    public Task<Uri> PutFile(String path, Uri uri) throws IOException {
        StorageReference newlyAdded = reference.child(path);


        Bitmap image = Picasso.get().load(uri).get();


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);

        System.out.println("Starting task..");
        UploadTask t = newlyAdded.putBytes(stream.toByteArray());
        t.addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("PAUSED...");
            }
        });



        System.out.println("recycling task..");
        image.recycle();


        System.out.println("returning..");
        return newlyAdded.getDownloadUrl();
    }
}
