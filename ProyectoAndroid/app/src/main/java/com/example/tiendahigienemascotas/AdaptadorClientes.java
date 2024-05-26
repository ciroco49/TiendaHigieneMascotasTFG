package com.example.tiendahigienemascotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiendahigienemascotas.Modelos.Cliente;

import java.util.List;

public class AdaptadorClientes extends ArrayAdapter<Cliente> {
    private List<Cliente> array_clientes;

    public AdaptadorClientes(Context contexto, List<Cliente> array_clientes) {
        super(contexto, R.layout.listview_clientes, array_clientes);
        this.array_clientes = array_clientes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View vista = inflater.inflate(R.layout.listview_clientes, parent, false);

        TextView DNI = vista.findViewById(R.id.dniCliente);
            DNI.setText(array_clientes.get(position).getDNI());
        TextView nombre = vista.findViewById(R.id.nombreCliente);
            nombre.setText(array_clientes.get(position).getNombre());

        return vista;

    }

    //Vacío el listview de clientes para rellenarlo de nuevo con los que proporciono
    public void actualizarClientes(List<Cliente> nuevosClientes) {
        array_clientes.clear();
        array_clientes.addAll(nuevosClientes);
        notifyDataSetChanged();
    }

    //Vacío el listview de clientes para rellenarlo de nuevo con el que proporciono
    public void actualizarCliente(Cliente cliente) {
        array_clientes.clear();
        array_clientes.add(cliente);
        notifyDataSetChanged();
    }

}
