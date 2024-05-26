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
import com.example.tiendahigienemascotas.Modelos.EspecialistaDTO;

import java.util.List;

public class AdaptadorEspecialistas extends ArrayAdapter<Cliente> {
    private List<EspecialistaDTO> array_especialistas;

    public AdaptadorEspecialistas(Context contexto, List<EspecialistaDTO> array_especialistas) {
        super(contexto, R.layout.listview_especialistas);
        this.array_especialistas = array_especialistas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View vista = inflater.inflate(R.layout.listview_especialistas, parent, false);

        TextView DNI = vista.findViewById(R.id.dniEspecialista);
            DNI.setText(array_especialistas.get(position).getDNI());
        TextView nombre = vista.findViewById(R.id.nombreEspecialista);
            nombre.setText(array_especialistas.get(position).getNombre());

        return vista;

    }
}
