package com.example.nachodelaviuda.festivaleoglobal.salasaza;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nachodelaviuda.festivaleoglobal.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Sala extends AppCompatActivity {

    private EditText mensaje;
    private TextView txtMensaje;



    DatabaseReference db;
    String claveTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);

        mensaje= (EditText)findViewById(R.id.editMensaje);
        txtMensaje= (TextView)findViewById(R.id.txtMensaje);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        db = FirebaseDatabase.getInstance().getReference().child("Chemical Music Festival");
        setTitle(" Sala - Chemical Music Festival");


        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public void enviar(View v)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        claveTemp = db.push().getKey();
        db.updateChildren(map);

        DatabaseReference child_ref = db.child(claveTemp);
        Map<String,Object> map2 = new HashMap<>();
       // map2.put("nombre",display);
        map2.put("mensaje", mensaje.getText().toString());
        child_ref.updateChildren(map2).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        mensaje.setText("");




    }
    public void append_chat(DataSnapshot ss)
    {
        String chat_msg,chat_username;
        Iterator i = ss.getChildren().iterator();
        while(i.hasNext())
        {
            chat_msg = ((DataSnapshot)i.next()).getValue().toString();
            //chat_username = ((DataSnapshot)i.next()).getValue().toString();
            chat_username = "NACHO";
            txtMensaje.append(chat_username + ": " +chat_msg + " \n");
        }
    }
}
