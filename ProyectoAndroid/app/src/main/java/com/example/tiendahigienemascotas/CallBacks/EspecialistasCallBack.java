package com.example.tiendahigienemascotas.CallBacks;

import com.example.tiendahigienemascotas.Modelos.EspecialistaDTO;

import java.util.List;

public interface EspecialistasCallBack {
    void onSuccessEspecialistas(List<EspecialistaDTO> listaEspecialistasDTO);
    void onSuccessEspecialista(EspecialistaDTO especialistaDTO);
    void onSuccessModEspecialista(String mensaje);
    void onError(String mensaje);

}
