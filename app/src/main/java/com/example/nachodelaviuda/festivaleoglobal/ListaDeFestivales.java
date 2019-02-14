package com.example.nachodelaviuda.festivaleoglobal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaDeFestivales extends AppCompatActivity {

    private ArrayList<ElementoLista> listaElementos;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ListaFestivalesAdaptador adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lista_festivales);
        listaElementos = new ArrayList<ElementoLista>();
        recyclerView = (RecyclerView) findViewById(R.id.reciclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ElementoLista elementoLista = new ElementoLista("nombre", "lugar",
                "https://firebasestorage.googleapis.com/v0/b/festivaleo-global.appspot.com/o/umf.png?alt=media&token=543bd3a4-2a3f-40d9-960b-877eaa7e72ca",
                3, "bibliografia", 2, 2, "nachoviuda@gmail.com");


        listaElementos.add(elementoLista);
        adapter = new ListaFestivalesAdaptador(ListaDeFestivales.this, listaElementos);

        reference = FirebaseDatabase.getInstance().getReference().child(Utilidades.proveniencia);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaElementos = new ArrayList<ElementoLista>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ElementoLista p = dataSnapshot1.getValue(ElementoLista.class);
                    listaElementos.add(p);
                }
                ImageView pape = (ImageView)findViewById(R.id.correocrea);
                adapter = new ListaFestivalesAdaptador(ListaDeFestivales.this, listaElementos);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intento = new Intent(ListaDeFestivales.this, GestorDeTabs.class);
                        intento.putExtra("nombre", listaElementos.get(recyclerView.getChildAdapterPosition(v)).getNombre());
                        intento.putExtra("lugar", listaElementos.get(recyclerView.getChildAdapterPosition(v)).getLugar());
                        intento.putExtra("img", listaElementos.get(recyclerView.getChildAdapterPosition(v)).getImagenId());
                        intento.putExtra("descripcion", listaElementos.get(recyclerView.getChildAdapterPosition(v)).getDescripcion());
                        intento.putExtra("correoCreador", listaElementos.get(recyclerView.getChildAdapterPosition(v)).getCorreoCreador());
                        Utilidades.nombreUbi = listaElementos.get(recyclerView.getChildAdapterPosition(v)).getNombre();
                        Utilidades.latitud = listaElementos.get(recyclerView.getChildAdapterPosition(v)).getLatitud();
                        Utilidades.longitud = listaElementos.get(recyclerView.getChildAdapterPosition(v)).getLongitud();
                        startActivity(intento);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListaDeFestivales.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
