package com.example.nachodelaviuda.festivaleoglobal;

import java.util.HashMap;
import java.util.Map;

public class ElementoValoracionUsuario {
    private String correoCreador;
    private float rate;

    public ElementoValoracionUsuario(String correoCreador, float rate) {
        this.correoCreador = correoCreador;
        this.rate = rate;
    }

    public ElementoValoracionUsuario() {
    }

    public Map<String, Object> toMep() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("autor", correoCreador);
        result.put("valoracion", rate);

        return result;
    }
}
