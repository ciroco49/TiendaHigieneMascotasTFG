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
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;

import java.util.List;

public class AdaptadorMascotas extends ArrayAdapter<Cliente> {
    private List<MascotaDTO> array_mascotas;

    public AdaptadorMascotas(Context contexto, List<MascotaDTO> array_mascotas) {
        super(contexto, R.layout.listview_mascotas);
        this.array_mascotas = array_mascotas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View vista = inflater.inflate(R.layout.listview_mascotas, parent, false);

        TextView DNI = vista.findViewById(R.id.dniMascota);
            DNI.setText(array_mascotas.get(position).getDNI());
        TextView nombre = vista.findViewById(R.id.nombreMascota);
            nombre.setText(array_mascotas.get(position).getNombre());

        return vista;

    }
}
