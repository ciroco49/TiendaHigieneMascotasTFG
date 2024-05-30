package com.example.tiendahigienemascotas.CallBacks;

import com.example.tiendahigienemascotas.Modelos.Cuenta;

public interface LoginCallBack {
    void onSuccess(Cuenta cuenta);
    void onSuccessRegistro(String mensaje);
    void existeCuentaLoggeada(boolean existe);
    void onError(String mensaje);
}
