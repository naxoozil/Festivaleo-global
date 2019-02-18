package com.example.nachodelaviuda.festivaleoglobal;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RecogerImagenes extends AppCompatActivity {
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    ImageView imageView;
    EditText edtImageName;
    private Uri uri;
    public static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_imagenes);
        mStorageRef = FirebaseStorage.getInstance().getReference("galeria" + Utilidades.nombreUbi);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("galeria" + Utilidades.nombreUbi);

        imageView = (ImageView) findViewById(R.id.nuevoImageView);
        edtImageName = (EditText) findViewById(R.id.txtImageName);

    }

    public void btnBrowse_click(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), REQUEST_CODE);
    }

    public void btnUpload_click(View view) {
        if (uri != null) {
            //Log.i("LOG1:", uri.toString());
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Subiendo imagen...");
            dialog.show();
            final StorageReference ref = mStorageRef.child(System.currentTimeMillis() + "." + getImageExt(uri));
            UploadTask uploadTask = ref.putFile(uri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        dialog.dismiss();
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        //Log.i("LOG2:", downloadUri.toString());
                        Toast.makeText(getApplicationContext(), "Imagen añadida satisfactoriamente", Toast.LENGTH_SHORT).show();
                        SubirImagen subirImagen = new SubirImagen(edtImageName.getText().toString(), downloadUri.toString());
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(subirImagen);
                        Intent intent = new Intent(getApplicationContext(), Galeria.class);
                        dialog.dismiss();
                        finish();
                    } else {

                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Selecciona una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bm);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}
