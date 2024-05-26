package com.example.tiendahigienemascotas.CallBacks;

import com.example.tiendahigienemascotas.Modelos.Cliente;

import java.util.List;

public interface ClientesCallBack {
    void onSuccessClientes(List<Cliente> array_clientes);
    void onSuccess(Cliente cliente);
    void onError(String mensaje);
}
