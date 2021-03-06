package com.example.nachodelaviuda.festivaleoglobal.chat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nachodelaviuda.festivaleoglobal.R;
import com.example.nachodelaviuda.festivaleoglobal.Utilidades;
import com.example.nachodelaviuda.festivaleoglobal.chat.models.Mensaje;
import com.example.nachodelaviuda.festivaleoglobal.chat.viewholder.HolderMensaje;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public abstract class ListaMensajes extends Fragment {
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    private FirebaseRecyclerAdapter<Mensaje, HolderMensaje> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public ListaMensajes() {}
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_lista_mensajes, container, false);
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]
        mRecycler = rootView.findViewById(R.id.messagesList);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        Query postsQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Mensaje>()
                .setQuery(postsQuery, Mensaje.class)
                .build();
        mAdapter = new FirebaseRecyclerAdapter<Mensaje, HolderMensaje>(options) {
            @Override
            public HolderMensaje onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new HolderMensaje(inflater.inflate(R.layout.item_mensaje, viewGroup, false));
            }
            @Override
            protected void onBindViewHolder(HolderMensaje viewHolder, int position, final Mensaje model) {
                final DatabaseReference postRef = getRef(position);
                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                // Bind Mensaje to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalPostRef = mDatabase.child(Utilidades.nombreSala+"/"+"posts").child(postRef.getKey());
                        DatabaseReference userPostRef = mDatabase.child(Utilidades.nombreSala+"/"+"user-posts").child(model.uid).child(postRef.getKey());
                        // Run two transactions
                        //onStarClicked(globalPostRef);
                        //onStarClicked(userPostRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }
    // [START post_stars_transaction]



    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
