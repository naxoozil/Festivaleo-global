package com.example.nachodelaviuda.festivaleoglobal.chat.fragment;

import com.example.nachodelaviuda.festivaleoglobal.Utilidades;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MensajesSalaDeChat extends ListaMensajes {

    public MensajesSalaDeChat() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query mensajesRecientes = databaseReference.child(Utilidades.nombreSala+"/"+"posts")
                .limitToFirst(1000);
        return mensajesRecientes;
    }
}
