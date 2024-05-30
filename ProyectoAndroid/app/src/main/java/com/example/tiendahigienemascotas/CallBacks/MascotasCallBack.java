package com.example.tiendahigienemascotas.CallBacks;

import com.example.tiendahigienemascotas.Modelos.MascotaDTO;

import java.util.List;

public interface MascotasCallBack {
    void onSuccessMascotas(List<MascotaDTO> listaMascotasDTO);
    void onSuccessMascota(MascotaDTO mascotaDTO);
    void onSuccessModMascota(String mensaje);
    void onError(String mensaje);
}
