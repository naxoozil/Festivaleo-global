package com.example.nachodelaviuda.festivaleoglobal;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PuntuarFestival extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button btnSubmit;
    private DatabaseReference mDatabase;
    private FirebaseUser usuario;
    private float sumaValoraciones;

    private float resultadoValoraciones;
    public ArrayList<ElementoValoracionUsuario> listaValoraciones;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_rating);
        //ratingBar.setRating(puntuacion.getFloat("puntuacion"));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Valoraciones-" + Utilidades.nombreUbi);
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        resultadoValoraciones = 0;
        listaValoraciones = new ArrayList<ElementoValoracionUsuario>();
        addListenerOnRatingBar();
        addListenerOnButton();
    }

    public void addListenerOnRatingBar() {
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                sumaValoraciones = 0;
                sumaValoraciones = rating;
            }
        });
    }
    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDatababaseListener();

                finish();
            }
        });
    }

    public void addDatababaseListener() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Utilidades.procedencia).child(Utilidades.nombreUbi);
        final Map<String, Object> childUpdates = new HashMap<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.e("pruebaRate",dataSnapshot1.toString());
                    Log.e("pruebaRate1",dataSnapshot1.getValue().toString());
                    Log.e("pruebaRate2",dataSnapshot1.getKey().toString());

                    if(dataSnapshot1.getKey().equals("correoCreador")){
                        String[] correo = usuario.getEmail().split("@");
                        childUpdates.put(dataSnapshot1.getKey(),usuario.getEmail());
                    }
                    if(dataSnapshot1.getKey().equals("rate")){
                        sumaValoraciones = ratingBar.getRating();
                        sumaValoraciones += Float.parseFloat(dataSnapshot1.getValue().toString());
                        resultadoValoraciones = sumaValoraciones / 2;
                        childUpdates.put("rate",resultadoValoraciones);
                        mDatabase = FirebaseDatabase.getInstance().getReference().child(Utilidades.procedencia).child(Utilidades.nombreUbi);
                        mDatabase.updateChildren(childUpdates);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PuntuarFestival.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
            }
        });
        String[] correo12 = usuario.getEmail().split("@");
        //mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("Valoraciones-" + Utilidades.nombreUbi);
        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("Valoraciones/" + Utilidades.nombreUbi);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String[] correo = usuario.getEmail().split("@");
                ElementoValoracionUsuario nuevaValoracion = new ElementoValoracionUsuario(correo[0], ratingBar.getRating());
                Map<String, Object> postValues = nuevaValoracion.toMep();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("Valoraciones/"+ "/" + Utilidades.nombreUbi, postValues);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });














    }

}
