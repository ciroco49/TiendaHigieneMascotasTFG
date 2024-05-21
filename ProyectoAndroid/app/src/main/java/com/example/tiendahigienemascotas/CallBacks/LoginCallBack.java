package com.example.tiendahigienemascotas.CallBacks;

import com.example.tiendahigienemascotas.Modelos.Cuenta;

public interface LoginCallBack {
    void onSuccess(Cuenta cuenta);
    void onError(String mensaje);
}
