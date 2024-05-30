package com.example.tiendahigienemascotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;

public class AdaptadorMascotas extends ArrayAdapter<MascotaDTO> {
    private List<MascotaDTO> array_mascotas;

    public AdaptadorMascotas(Context contexto, List<MascotaDTO> array_mascotas) {
        super(contexto, R.layout.listview_mascotas, array_mascotas);
        this.array_mascotas = array_mascotas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.listview_mascotas, parent, false);

        MascotaDTO mascota = array_mascotas.get(position);

        TextView dni = convertView.findViewById(R.id.dniMascota);
            dni.setText(mascota.getDNI());
        TextView nombre = convertView.findViewById(R.id.nombreMascota);
            nombre.setText(mascota.getNombre());

        return convertView;
    }

    //Vacío el listview de mascotas para rellenarlo de nuevo con las que proporciono
    public void actualizarMascotas(List<MascotaDTO> nuevasMascotasDTO) {
        array_mascotas.clear();
        array_mascotas.addAll(nuevasMascotasDTO);
        notifyDataSetChanged();
    }

    //Vacío el listview de mascotas para rellenarlo de nuevo con la que proporciono
    public void actualizarMascota(MascotaDTO mascotaDTO) {
        array_mascotas.clear();
        array_mascotas.add(mascotaDTO);
        notifyDataSetChanged();
    }



}
