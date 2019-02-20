package com.example.nachodelaviuda.festivaleoglobal.chat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nachodelaviuda.festivaleoglobal.R;
import com.example.nachodelaviuda.festivaleoglobal.Utilidades;
import com.example.nachodelaviuda.festivaleoglobal.chat.models.Mensaje;
import com.example.nachodelaviuda.festivaleoglobal.chat.models.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NuevoMensaje extends BaseActivity {

    private static final String TAG = "NuevoMensaje";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_mensaje);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // [END initialize_database_ref]

        mTitleField = findViewById(R.id.fieldTitle);
        mSubmitButton = findViewById(R.id.subirMsg);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        final String title = mTitleField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Subiendo...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get usuario value
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);

                        // [START_EXCLUDE]
                        if (usuario == null) {
                            // Usuario is null, error out
                            Log.e(TAG, "Error " + userId + " is unexpectedly null");
                            Toast.makeText(NuevoMensaje.this,
                                    "Error con el usuario",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, usuario.username, title);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.show();
        } else {
            mSubmitButton.hide();
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title) {
        // Create new mensaje at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child(Utilidades.nombreSala+"/"+"sala1"+"/"+"posts").push().getKey();
        Mensaje mensaje = new Mensaje(userId, username, title);
        Map<String, Object> postValues = mensaje.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(Utilidades.nombreSala+"/"+"/posts/" + key, postValues);
        childUpdates.put(Utilidades.nombreSala+"/"+"/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}
