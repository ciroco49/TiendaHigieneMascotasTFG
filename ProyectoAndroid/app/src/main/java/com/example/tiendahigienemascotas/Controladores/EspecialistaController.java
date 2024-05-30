package com.example.tiendahigienemascotas.Controladores;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tiendahigienemascotas.CallBacks.ClientesCallBack;
import com.example.tiendahigienemascotas.CallBacks.EspecialistasCallBack;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.EspecialistaDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EspecialistaController {

    public static void getEspecialistas(Context contexto, EspecialistasCallBack callBack) {
        RequestQueue queue = Volley.newRequestQueue(contexto);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://192.168.68.101:8080/especialistas",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response == null || response.isEmpty()) {
                                Log.e("GetEspecialistas Error: ", "Respuesta vacía o nula");
                                callBack.onError("No se han encontrado especialistas");
                            }
                            Gson gson = new Gson();
                            Type lista = new TypeToken<List<EspecialistaDTO>>() {}.getType();
                            List<EspecialistaDTO> array_especialistas = gson.fromJson(response, lista);
                            callBack.onSuccessEspecialistas(array_especialistas);
                        } catch (Exception e) {
                            Log.e("GetEspecialistas Error", "Error parseando el JSON", e);
                            callBack.onError("Error parseando el JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetEspecialistas Error", "Error en la petición: " + error.toString());
                        callBack.onError("Error en la petición: " + error.toString());
                    }

                })

        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed = new String(response.data, StandardCharsets.UTF_8);
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        queue.add(stringRequest);
    }

}
