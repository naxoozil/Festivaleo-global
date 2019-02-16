package com.example.nachodelaviuda.festivaleoglobal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nachodelaviuda.festivaleoglobal.chat.Mensajeria;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Principal extends AppCompatActivity {
    DatabaseReference db;
    ArrayList<String> salas;
    EditText editSala;
    ListView listSala;
    ArrayAdapter<String> adapSalas;
    FirebaseUser usu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        editSala = (EditText)findViewById(R.id.editSala);
        listSala = (ListView)findViewById(R.id.listSala);
        salas = new ArrayList<>();
        adapSalas = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,salas);
        listSala.setAdapter(adapSalas);
        db = FirebaseDatabase.getInstance().getReference().child("salas" + Utilidades.nombreUbi);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }
                salas.clear();
                salas.addAll(set);
                adapSalas.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Principal.this, "No se encuentra conexión a Internet", Toast.LENGTH_SHORT).show();
            }
        });
        listSala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                usu = FirebaseAuth.getInstance().getCurrentUser();
                Utilidades.nombreSala = Utilidades.nombreUbi + ((TextView) view).getText().toString();
                Log.e("NOMBRESALA",  Utilidades.nombreSala);
                Intent intent = new Intent(Principal.this, Mensajeria.class);
                startActivity(intent);
            }
        });
    }
    public void insert_data(View v) {
        Map<String,Object> map = new HashMap<>();
        map.put(editSala.getText().toString(), "");
        db.updateChildren(map);
        editSala.setText("");
    }


    }


