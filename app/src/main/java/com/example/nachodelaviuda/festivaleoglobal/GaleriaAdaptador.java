package com.example.nachodelaviuda.festivaleoglobal;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GaleriaAdaptador extends RecyclerView.Adapter<GaleriaAdaptador.ViewHolderGaleria> {

    private Context mContext;
    //private List<SubirImagen> mData;
    private ArrayList<SubirImagen> mData;

    //public GaleriaAdaptador(Context mContext, List<SubirImagen> mData) {
    public GaleriaAdaptador(Context mContext, ArrayList<SubirImagen> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public ViewHolderGaleria onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, null, false);
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.fragment_fragmento_galeria, parent, false);
        return new ViewHolderGaleria(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderGaleria holder, final int position) {
        holder.nombre.setText(mData.get(position).getName());
        Glide.with(mContext).load(mData.get(position).getUrl()).into(holder.fotoimg);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolderGaleria extends RecyclerView.ViewHolder {
        ImageView fotoimg;
        TextView nombre;
        CardView cardView;

        public ViewHolderGaleria(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombreFoto);
            fotoimg = (ImageView) itemView.findViewById(R.id.fotoGaleria);
            cardView = (CardView) itemView.findViewById(R.id.cardGaleria);

        }
    }


}
