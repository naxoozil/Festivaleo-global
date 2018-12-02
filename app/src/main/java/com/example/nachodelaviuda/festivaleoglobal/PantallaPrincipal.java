
package com.example.nachodelaviuda.festivaleoglobal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PantallaPrincipal extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_principal,container,false);
        Button butEur = (Button) vista.findViewById(R.id.butEuropa);
        Button butNor = (Button) vista.findViewById(R.id.butNorAm);
        Button butSur = (Button) vista.findViewById(R.id.butSurAm);
        Button butAsia = (Button) vista.findViewById(R.id.butAsia);

        butEur.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Utilidades.proveniencia = "europa";
                Intent intentoEuropa = new Intent(getContext(), ListaDeFestivales.class);
                startActivity(intentoEuropa);
            }
        });
        butNor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Utilidades.proveniencia = "norteamerica";
                Intent intentoAmNor = new Intent(getContext(), ListaDeFestivales.class);
                startActivity(intentoAmNor);
            }
        });
        butSur.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Utilidades.proveniencia = "latinoamerica";
                Intent intentoAmericaSur = new Intent(getContext(), ListaDeFestivales.class);
                startActivity(intentoAmericaSur);
            }
        });
        butAsia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Utilidades.proveniencia = "asia";
                Intent intentoAsia = new Intent(getContext(), ListaDeFestivales.class);
                startActivity(intentoAsia);
            }
        });


        return vista;

    }
}

