package com.example.nachodelaviuda.festivaleoglobal;

import java.util.HashMap;
import java.util.Map;

public class ElementosPantallaPrincipal {
    private String nombre;
    private String lugar;
    private String imagenId;
    private String procedencia;

    public ElementosPantallaPrincipal(){}
    public ElementosPantallaPrincipal(String nombre, String lugar, String imagenId, String procedencia) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.imagenId = imagenId;
        this.procedencia = procedencia;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nombre", nombre);
        result.put("lugar", lugar);
        result.put("imagenId", imagenId);
        result.put("procedencia", procedencia);
        return result;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getImagenId() {
        return imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }
}
