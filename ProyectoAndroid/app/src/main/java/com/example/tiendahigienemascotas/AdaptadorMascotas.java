package com.example.tiendahigienemascotas;

import android.content.Context;
import android.util.Log;
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

public class AdaptadorMascotas extends ArrayAdapter<MascotaDTO> {
    private List<MascotaDTO> array_mascotas;

    public AdaptadorMascotas(Context contexto, List<MascotaDTO> array_mascotas) {
        super(contexto, R.layout.listview_mascotas, array_mascotas);
        this.array_mascotas = array_mascotas;
    }

    private static class ViewHolder {
        TextView DNI;
        TextView nombre;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_mascotas, parent, false);
            holder = new ViewHolder();
            holder.DNI = convertView.findViewById(R.id.dniMascota);
            holder.nombre = convertView.findViewById(R.id.nombreMascota);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MascotaDTO mascota = array_mascotas.get(position);
        holder.DNI.setText(mascota.getDNI());
        holder.nombre.setText(mascota.getNombre());

        Log.d("getView DNI_Mascota", mascota.getDNI());
        Log.d("getView Nombre_Mascota", mascota.getNombre());

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