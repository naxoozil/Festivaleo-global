package com.example.nachodelaviuda.festivaleoglobal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentoGeneral extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "descripcion";


    private String mParam1;
    private String mParam2;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private OnFragmentInteractionListener mListener;

    public FragmentoGeneral() {
        // Required empty public constructor
    }


    public static FragmentoGeneral newInstance(String param1, String param2) {
        FragmentoGeneral fragment = new FragmentoGeneral();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista = inflater.inflate(R.layout.fragment_fragmento_general, container, false);
        final TextView txt = (TextView) vista.findViewById(R.id.fragGeneralNombreFestival);
        final TextView txtdesc = (TextView) vista.findViewById(R.id.generalDescripcion);
        ImageView imagen = (ImageView) vista.findViewById(R.id.fragGeneral);
        Bundle datos = this.getActivity().getIntent().getExtras();
        txt.setText(datos.getString("nombre"));
        txtdesc.setText(datos.getString("descripcion"));
        Glide.with(getContext()).load(datos.getString("img")).into(imagen);

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
