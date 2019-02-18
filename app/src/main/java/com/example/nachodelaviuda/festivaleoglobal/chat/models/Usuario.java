package com.example.nachodelaviuda.festivaleoglobal.chat.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class Usuario {

    public String username;
    public String email;

    public Usuario() {
        // Default constructor required for calls to DataSnapshot.getValue(Usuario.class)
    }

    public Usuario(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
// [END blog_user_class]
