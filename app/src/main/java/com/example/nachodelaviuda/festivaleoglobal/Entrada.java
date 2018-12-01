package com.example.nachodelaviuda.festivaleoglobal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class Entrada extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            // 5 segundos
            int DURACION = 3000;
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    // Cuando pasen los 5 segundos, pasamos a la actividad principal de la aplicación
                    Intent intent = new Intent(Entrada.this, LogIn.class);
                    startActivity(intent);
                    finish();
                };
            }, DURACION);
        }else{
            // 3 segundos
            int DURACION2 = 5000;
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                    Intent intent = new Intent(Entrada.this, LogIn.class);
                    startActivity(intent);
                    finish();
                };
            }, DURACION2);
        }


    }
}
