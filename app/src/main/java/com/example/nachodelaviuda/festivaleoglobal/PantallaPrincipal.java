
package com.example.nachodelaviuda.festivaleoglobal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PantallaPrincipal extends Fragment {
    private TextView nombrePrincipal;
    private TextView lugarPrincipal;
    private ImageView fotoPrincipal;
    private DatabaseReference mDatabase;
    private FirebaseUser usuario;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_principal,container,false);
        nombrePrincipal = (TextView)vista.findViewById(R.id.tVNombre);
        lugarPrincipal = (TextView)vista.findViewById(R.id.tvLugar);
        fotoPrincipal = (ImageView) vista.findViewById(R.id.imgPrincipal);
        fotoPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //usuario = FirebaseAuth.getInstance().getCurrentUser();
        //final String[] correo = usuario.getEmail().split("@");
        mDatabase = FirebaseDatabase.getInstance().getReference("Ultimos Festivales Visitados");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    usuario = FirebaseAuth.getInstance().getCurrentUser();
                    final String[] correo = usuario.getEmail().split("@");
                    if (dataSnapshot1.getKey().equals(correo[0])){
                        //usuario = FirebaseAuth.getInstance().getCurrentUser();
                        //final String[] correo = usuario.getEmail().split("@");
                        Log.e("datasnapshot", dataSnapshot.toString());
                        Log.e("datasnapshot1", dataSnapshot1.toString());
                        if(dataSnapshot.toString().contains(correo[0]) && dataSnapshot != null ){
                            ElementosPantallaPrincipal pantalla = dataSnapshot1.getValue(ElementosPantallaPrincipal.class);
                            nombrePrincipal.setText(pantalla.getNombre());
                            lugarPrincipal.setText(pantalla.getLugar());
                            Glide.with(getContext()).load(pantalla.getImagenId()).into(fotoPrincipal);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return vista;

    }
}

