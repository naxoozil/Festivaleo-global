
package com.example.nachodelaviuda.festivaleoglobal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child(usuario.getEmail());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ElementosPantallaPrincipal pantalla = dataSnapshot1.getValue(ElementosPantallaPrincipal.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        /*Button butEur = (Button) vista.findViewById(R.id.butEuropa);
        Button butNor = (Button) vista.findViewById(R.id.butNorAm);
        Button butSur = (Button) vista.findViewById(R.id.butSurAm);
        Button butAsia = (Button) vista.findViewById(R.id.butAsia);

        butEur.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Utilidades.proveniencia = "europa";
                Intent intentoEuropa = new Intent(getContext(), ListaDeFestivales.class);
                startActivity(intentoEuropa);
            }
        });
        butNor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Utilidades.proveniencia = "norteamerica";
                Intent intentoAmNor = new Intent(getContext(), ListaDeFestivales.class);
                startActivity(intentoAmNor);
            }
        });
        butSur.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Utilidades.proveniencia = "latinoamerica";
                Intent intentoAmericaSur = new Intent(getContext(), ListaDeFestivales.class);
                startActivity(intentoAmericaSur);
            }
        });
        butAsia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Utilidades.proveniencia = "asia";
                Intent intentoAsia = new Intent(getContext(), ListaDeFestivales.class);
                startActivity(intentoAsia);
            }
        });

*/
        return vista;

    }
}

