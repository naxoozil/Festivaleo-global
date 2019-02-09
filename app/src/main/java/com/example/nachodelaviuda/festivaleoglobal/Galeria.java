package com.example.nachodelaviuda.festivaleoglobal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Galeria extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private List<GaleriaElemento> imgList;
    private ListView lv;
    private GaleriaAdaptadorLista adapter;
    private ProgressDialog progressDialog;
    private FloatingActionButton actionGaleria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);


        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.galeria);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("galeria" + Utilidades.nombreUbi);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    GaleriaElemento img = dataSnapshot1.getValue(GaleriaElemento.class);
                    imgList.add(img);
                }
                adapter = new GaleriaAdaptadorLista(Galeria.this, R.layout.image_item, imgList);
                lv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subir_img, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.subirImagen) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Galeria.this, RecogerImagenes.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}