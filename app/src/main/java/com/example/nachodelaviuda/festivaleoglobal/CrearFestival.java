package com.example.nachodelaviuda.festivaleoglobal;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CrearFestival extends AppCompatActivity {
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    private StorageReference mStorageRef;
    private FirebaseUser usuario;

    private EditText edtNombreFestival;
    private EditText edtLugarFestival;
    private EditText edtLatitudFestival;
    private EditText edtLongitudFestival;
    private EditText edtDescripcionFestival;
    private ImageView imagenFestival;
    private Button buscarImagen;
    private Button subirFestival;
    public static final int REQUEST_CODE = 1234;
    private Uri uri;
    DatabaseReference mDatabaseRef;
    private boolean comprobacion;
    private String enlaceDeImagen;
    private Spinner spinnerContinente;
    private Spinner spinnerEstrellas;
    private String elementoSeleccionado;
    private String elementoSeleccionado2;
    public CrearFestival() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_festival);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        edtNombreFestival = (EditText) findViewById(R.id.nombreFestival);
        edtLugarFestival = (EditText) findViewById(R.id.lugarFestival);
        edtLatitudFestival = (EditText) findViewById(R.id.latitudFestival);
        edtLongitudFestival = (EditText) findViewById(R.id.longitudFestival);
        edtDescripcionFestival = (EditText) findViewById(R.id.descripcionFestival);

        elementoSeleccionado = "";
        spinnerContinente = (Spinner) findViewById(R.id.spinnerContinente);
        String[] letra = {"Europa","NorteAmerica","Latinoamerica","Asia"};
        spinnerContinente.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));
        spinnerContinente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                elementoSeleccionado = adapterView.getItemAtPosition(pos).toString().toLowerCase();
                Log.e(elementoSeleccionado,elementoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });

        elementoSeleccionado2 = "";
        spinnerEstrellas = (Spinner) findViewById(R.id.spinnerEstrellas);
        String[] letra2 = {"1","2","3","4","5"};
        spinnerEstrellas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra2));
        spinnerEstrellas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                elementoSeleccionado2 = adapterView.getItemAtPosition(pos).toString();
                Log.e(elementoSeleccionado2,elementoSeleccionado2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });
        imagenFestival = (ImageView) findViewById(R.id.nuevoImageView);
        buscarImagen = (Button) findViewById(R.id.buscarImagen);
        subirFestival = (Button) findViewById(R.id.btnSubirFestival);
        subirFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobaciones();
                if(comprobacion) {
                    btnSubirfestival();
                }
            }
        });
    }

    private boolean comprobaciones(){
        if(edtNombreFestival.getText().toString().equals("") || edtLugarFestival.getText().toString().equals("") ||
            edtLatitudFestival.getText().toString().equals("") || edtLongitudFestival.getText().toString().equals("") ||
            edtDescripcionFestival.getText().toString().equals("") || imagenFestival == null){
            //algún campo esta vacío o no hay foto
            Toast.makeText(getApplicationContext(), "Rellena los campos" , Toast.LENGTH_SHORT).show();
            comprobacion = false;
        }
        else{
            comprobacion = true;
        }
        return comprobacion;
    }
    private void subirNuevoFestival() {
        ElementoLista festival = new ElementoLista(edtNombreFestival.getText().toString(),
                edtLugarFestival.getText().toString(),enlaceDeImagen,
                Integer.parseInt(elementoSeleccionado2), edtDescripcionFestival.getText().toString(),
                Integer.parseInt(edtLatitudFestival.getText().toString()),
                Integer.parseInt(edtLongitudFestival.getText().toString()),
                usuario.getEmail());
        Map<String, Object> postValues = festival.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(elementoSeleccionado + "/" + "/" + edtNombreFestival.getText().toString() + "/", postValues);

        mDatabase.updateChildren(childUpdates);
    }

    public void btnBuscarImagen(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleciona una imagen"), REQUEST_CODE);
    }

    public void btnSubirfestival() {
        if (uri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Subiendo festival...");
            dialog.show();
            mStorageRef = FirebaseStorage.getInstance().getReference("nuevosFestivales");
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
                        Toast.makeText(getApplicationContext(), "Imagen añadida satisfactoriamente", Toast.LENGTH_SHORT).show();
                        enlaceDeImagen = downloadUri.toString();
                        String uploadId = mDatabaseRef.push().getKey();
                        //mDatabaseRef.child(uploadId).setValue(subirFestival);
                        subirNuevoFestival();
                        dialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Selecciona una imagen", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Por favor selecciona una imagen para el festival", Toast.LENGTH_SHORT).show();
        }
    }
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imagenFestival.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}