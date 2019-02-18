package com.example.nachodelaviuda.festivaleoglobal.chat.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nachodelaviuda.festivaleoglobal.R;
import com.example.nachodelaviuda.festivaleoglobal.chat.models.Mensaje;

public class HolderMensaje extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;


    public HolderMensaje(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.postTitle);
        authorView = itemView.findViewById(R.id.postAuthor);

    }

    public void bindToPost(Mensaje mensaje, View.OnClickListener starClickListener) {
        titleView.setText(mensaje.title);
        authorView.setText(mensaje.author);

    }
}
