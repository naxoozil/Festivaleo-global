package com.example.nachodelaviuda.festivaleoglobal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ListaFestivalesAdaptador extends RecyclerView.Adapter<ListaFestivalesAdaptador.ElementoViewHolder>
        implements View.OnClickListener {

    ArrayList<ElementoLista> listaElementos;
    Context context;
    private View.OnClickListener listener;

    public ListaFestivalesAdaptador(Context context, ArrayList<ElementoLista> listaElementos) {
        this.context = context;
        this.listaElementos = listaElementos;

    }

    @Override
    public ElementoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, null, false);

        view.setOnClickListener(this);
        return new ElementoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ElementoViewHolder holder, int position) {
        holder.txtNombre.setText(listaElementos.get(position).getNombre());
        holder.txtLugar.setText(listaElementos.get(position).getLugar());
        Glide.with(context).load(listaElementos.get(position).getImagenId()).into(holder.foto);
        holder.ratingBar.setRating(listaElementos.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return listaElementos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
                listener.onClick(v);
        }
    }

    public class ElementoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtLugar;
        ImageView foto;
        RatingBar ratingBar;

        public ElementoViewHolder(View itemView) {
            super(itemView);
            txtNombre     = (TextView) itemView.findViewById(R.id.elementoNombre);
            txtLugar      = (TextView) itemView.findViewById(R.id.elementoLugar);
            foto          = (ImageView) itemView.findViewById(R.id.elementoImagen);
            ratingBar     = (RatingBar) itemView.findViewById(R.id.elementoRating);
        }
    }
}
