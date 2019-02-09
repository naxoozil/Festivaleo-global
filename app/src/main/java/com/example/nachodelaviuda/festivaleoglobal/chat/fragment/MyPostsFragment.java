package com.example.nachodelaviuda.festivaleoglobal.chat.fragment;

import com.example.nachodelaviuda.festivaleoglobal.Utilidades;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyPostsFragment extends PostListFragment {

    public MyPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child(Utilidades.nombreSala+"/"+"user-posts")
                .child(getUid());
    }
}
