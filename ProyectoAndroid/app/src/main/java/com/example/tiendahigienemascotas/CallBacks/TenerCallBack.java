package com.example.tiendahigienemascotas.CallBacks;

import com.example.tiendahigienemascotas.Modelos.TenerDTO;

import java.util.List;

public interface TenerCallBack {
    void onSuccess(List<TenerDTO> listaTener);
    void onError(String mensaje);
}
