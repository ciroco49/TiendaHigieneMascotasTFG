package com.example.tiendahigienemascotas.CallBacks;

import com.example.tiendahigienemascotas.Modelos.CuentaDTO;

public interface LoginCallBack {
    void onSuccess(CuentaDTO cuentaDTO);
    void onSuccessRegistro(String mensaje);
    void existeCuentaLoggeada(boolean existe);
    void onError(String mensaje);
}
