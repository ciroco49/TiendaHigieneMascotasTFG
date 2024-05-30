package com.example.tiendahigienemascotas.Controladores;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tiendahigienemascotas.CallBacks.TenerCallBack;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;
import com.example.tiendahigienemascotas.Modelos.TenerDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenerController {

    public static void getTenerPorDNI_Cliente(Cliente cliente, Context contexto, TenerCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.68.101:8080/tenerPorDNICliente",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response == null || response.isEmpty()) {
                            Log.e("GetTenerPorDNI_Cliente Error", "Respuesta vacía o nula");
                            callBack.onError("No existen mascotas para dicho cliente");
                            return;
                        }

                        Log.d("GetTenerPorDNI_Cliente Response", response);
                        Gson gson = new Gson();
                        Type lista = new TypeToken<List<TenerDTO>>() {}.getType();
                        List<TenerDTO> tenerList = gson.fromJson(response, lista);
                        callBack.onSuccess(tenerList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GetTenerPorDNI_Cliente Error", "Error en la petición: " + error.toString());
                callBack.onError(error.toString());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("dni", cliente.getDNI());
                    String requestBody = jsonBody.toString();
                    return requestBody.getBytes(StandardCharsets.UTF_8);
                } catch (JSONException e) {
                    Log.e("Body error: ", e.toString());
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed = new String(response.data, StandardCharsets.UTF_8);
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        RequestQueue queue = Volley.newRequestQueue(contexto);
        queue.add(stringRequest);
        
    }

    public static void getTenerPorDNI_Mascota(MascotaDTO mascotaDTO, Context contexto, TenerCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.68.101:8080/tenerPorDNIMascota",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response == null || response.isEmpty()) {
                            Log.e("GetTenerPorDNI_Mascota Error", "Respuesta vacía o nula");
                            callBack.onError("No existen clientes para dicha mascota");
                            return;
                        }

                        Log.d("GetTenerPorDNI_Mascota Response", response);
                        Gson gson = new Gson();
                        Type lista = new TypeToken<List<TenerDTO>>() {}.getType();
                        List<TenerDTO> tenerList = gson.fromJson(response, lista);
                        callBack.onSuccess(tenerList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GetTenerPorDNI_Mascota Error", "Error en la petición: " + error.toString());
                callBack.onError(error.toString());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("dni", mascotaDTO.getDNI());
                    String requestBody = jsonBody.toString();
                    return requestBody.getBytes(StandardCharsets.UTF_8);
                } catch (JSONException e) {
                    Log.e("Body error: ", e.toString());
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed = new String(response.data, StandardCharsets.UTF_8);
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        RequestQueue queue = Volley.newRequestQueue(contexto);
        queue.add(stringRequest);

    }

}
