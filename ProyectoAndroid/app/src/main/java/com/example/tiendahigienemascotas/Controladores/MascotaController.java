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
import com.example.tiendahigienemascotas.CallBacks.ClientesCallBack;
import com.example.tiendahigienemascotas.CallBacks.MascotasCallBack;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MascotaController {

    public static void getMascotas(Context contexto, MascotasCallBack callBack) {
        RequestQueue queue = Volley.newRequestQueue(contexto);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://192.168.68.101:8080/mascotas",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response == null || response.isEmpty()) {
                                Log.e("GetMascotas Error: ", "Respuesta vacía o nula");
                                callBack.onError("No se han encontrado mascotas");
                            }
                            Gson gson = new Gson();
                            Type lista = new TypeToken<List<MascotaDTO>>() {}.getType();
                            List<MascotaDTO> array_mascotas = gson.fromJson(response, lista);
                            callBack.onSuccessMascotas(array_mascotas);
                        } catch (Exception e) {
                            Log.e("GetMascotas Error", "Error parseando el JSON", e);
                            callBack.onError("Error parseando el JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetMascotas Error", "Error en la petición: " + error.toString());
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

    public static void getMascotaPorDNI(String DNI, Context contexto, MascotasCallBack callBack) {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.68.101:8080/mascotaPorDni",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response == null || response.isEmpty()) {
                                Log.e("GetMascotaPorDNI Error", "Respuesta vacía o nula");
                                callBack.onError("No existe una mascota con el DNI proporcionado");
                                return;
                            }

                            Log.d("GetMascotaPorDNI Response", response);
                            Gson gson = new Gson();
                            MascotaDTO mascotaDTO = gson.fromJson(response, MascotaDTO.class);
                            Log.d("Mascota", mascotaDTO.toString());
                            callBack.onSuccessMascota(mascotaDTO);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("GetClientePorDNI Error", "Error en la petición: " + error.toString());
                    callBack.onError("Error en la petición: " + error.toString());
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("dni", DNI);
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

        } catch (Exception ex) {
            Log.d("Error login: ", ex.getMessage());
        }
    }

    public static void getMascotasPorEspecie(String especie, Context contexto, MascotasCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.68.101:8080/mascotaPorEspecie",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response == null || response.isEmpty()) {
                                Log.e("GetMascotasPorEspecie Error: ", "Respuesta vacía o nula");
                                callBack.onError("No existen mascotas con la especie proporcionada");
                            }
                            Gson gson = new Gson();
                            Type lista = new TypeToken<List<MascotaDTO>>() {}.getType();
                            List<MascotaDTO> array_mascotas = gson.fromJson(response, lista);
                            callBack.onSuccessMascotas(array_mascotas);
                        } catch (Exception e) {
                            Log.e("GetMascotasPorEspecie Error", "Error parseando el JSON", e);
                            callBack.onError("Error parseando el JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetMascotasPorEspecie Error", "Error en la petición: " + error.toString());
                        callBack.onError("Error en la petición: " + error.toString());
                    }
                })  {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("especie", especie);
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

    public static void getMascotasPorRaza(String raza, Context contexto, MascotasCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.68.101:8080/mascotaPorRaza",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response == null || response.isEmpty()) {
                                Log.e("GetMascotasPorRaza Error: ", "Respuesta vacía o nula");
                                callBack.onError("No existen mascotas con la raza proporcionada");
                            }
                            Gson gson = new Gson();
                            Type lista = new TypeToken<List<MascotaDTO>>() {}.getType();
                            List<MascotaDTO> array_mascotas = gson.fromJson(response, lista);
                            callBack.onSuccessMascotas(array_mascotas);
                        } catch (Exception e) {
                            Log.e("GetMascotasPorRaza Error", "Error parseando el JSON", e);
                            callBack.onError("Error parseando el JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetMascotasPorRaza Error", "Error en la petición: " + error.toString());
                        callBack.onError("Error en la petición: " + error.toString());
                    }
                })  {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("raza", raza);
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
