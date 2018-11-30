package com.example.nachodelaviuda.festivaleoglobal;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GaleriaAdaptadorLista extends ArrayAdapter<GaleriaElemento> {

    private Activity context;
    private int resource;
    private List<GaleriaElemento> listImage;


    public GaleriaAdaptadorLista(@NonNull Activity context, @LayoutRes int resource, @NonNull List<GaleriaElemento> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource, null);
        TextView tvname = v.findViewById(R.id.tvImageName);
        ImageView img = v.findViewById(R.id.imgView);
        tvname.setText(listImage.get(position).getName());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);
        return v;

    }

}
