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
import android.widget.RatingBar;

import com.example.nachodelaviuda.festivaleoglobal.chat.Mensajeria;

public class FragmentoOtros extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;
    private RatingBar rtbar;

    public FragmentoOtros() {

    }


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
        rtbar = (RatingBar)vista.findViewById(R.id.otroRating);
        ImageView imgMapa = (ImageView) vista.findViewById(R.id.imagenMapa);
        ImageView imgGaleria = (ImageView) vista.findViewById(R.id.imagenGaleria);
        ImageView imgchat = (ImageView)vista.findViewById(R.id.imagenChat);

        //Lleva a la activity del maps
        imgMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llevarAMapa = new Intent(getContext(),Maps.class);
                startActivity(llevarAMapa);
            }
        });
        imgGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llevarAGaleria = new Intent(getContext(),Galeria.class);
                startActivity(llevarAGaleria);
            }
        });
        imgchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Mensajeria.class);
                startActivity(intent);
            }
        });

        return vista;
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