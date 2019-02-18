package com.example.nachodelaviuda.festivaleoglobal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentoOtros extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;

    public FragmentoOtros() {}

    public static FragmentoOtros newInstance(String param1, String param2) {
        FragmentoOtros fragment = new FragmentoOtros();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_fragment_otros, container, false);
        TextView puntuar      = (TextView) vista.findViewById(R.id.puntuar);
        TextView ubi          = (TextView) vista.findViewById(R.id.ubi);
        TextView gal          = (TextView) vista.findViewById(R.id.gal);
        TextView chat         = (TextView) vista.findViewById(R.id.chat);
        ImageView imgEstrella = (ImageView) vista.findViewById(R.id.imagenEstrella);
        ImageView imgMapa     = (ImageView) vista.findViewById(R.id.imagenMapa);
        ImageView imgGaleria  = (ImageView) vista.findViewById(R.id.imagenGaleria);
        ImageView imgchat     = (ImageView)vista.findViewById(R.id.imagenChat);

        puntuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puntuarFestival();
            }
        });
        ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irUbicacion();
            }
        });
        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeria();
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salasDeChat();
            }
        });

        imgEstrella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puntuarFestival();
            }
        });

        imgMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irUbicacion();
            }
        });
        imgGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeria();
            }
        });
        imgchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salasDeChat();
            }
        });

        return vista;
    }
    public void puntuarFestival(){
        Intent aPuntuar = new Intent(getContext(), PuntuarFestival.class);
        startActivity(aPuntuar);
    }
    public void irUbicacion(){
        Intent llevarAMapa = new Intent(getContext(),Maps.class);
        startActivity(llevarAMapa);
    }
    public void galeria(){
        Intent llevarAGaleria = new Intent(getContext(),Galeria.class);
        startActivity(llevarAGaleria);
    }
    public void salasDeChat(){
        Intent intent = new Intent(getContext(), SalasDeChat.class);
        startActivity(intent);
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}