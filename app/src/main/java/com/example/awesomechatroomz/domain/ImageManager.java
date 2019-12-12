package com.example.awesomechatroomz.domain;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class ImageManager {

    private StorageReference reference;

    @Inject
    public ImageManager(StorageReference reference) {
        this.reference = reference;
    }

    Task<Uri> uploadFile(String path, Uri uri) throws IOException {
        StorageReference newlyAdded = reference.child(path);


        Bitmap image = Picasso.get().load(uri).get();


        return uploadFile(path, image);
    }

    public interface URLRequestListener {
        void onSuccess(Uri uri);
    }

    void requestUri(final String path, final URLRequestListener listener) throws IOException {
        StorageReference toFile = reference.child(path);

        toFile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                listener.onSuccess(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Executors.newSingleThreadExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            requestUri(path, listener);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    Task<Uri> uploadFile(String path, Bitmap image) throws IOException {
        StorageReference newlyAdded = reference.child(path);


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);

        UploadTask t = newlyAdded.putBytes(stream.toByteArray());
        t.addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
            }
        });

        image.recycle();


        return newlyAdded.getDownloadUrl();
    }
}
