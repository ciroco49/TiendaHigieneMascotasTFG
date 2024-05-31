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
import com.example.tiendahigienemascotas.CallBacks.EspecialistasCallBack;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.EspecialistaDTO;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EspecialistaController {

    public static void getEspecialistas(Context contexto, EspecialistasCallBack callBack) {
        RequestQueue queue = Volley.newRequestQueue(contexto);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://" + PreferenciasCompartidas.obtenerIP(contexto) + ":8080/especialistas",
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

    public static void getEspecialistaPorDNI(String DNI, Context contexto, EspecialistasCallBack callBack) {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "http://" + PreferenciasCompartidas.obtenerIP(contexto) + ":8080/especialistaPorDni",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response == null || response.isEmpty()) {
                                Log.e("GetEspecialistaPorDNI Error", "Respuesta vacía o nula");
                                callBack.onError("No existe un especialista con el DNI proporcionado");
                                return;
                            }

                            Log.d("GetEspecialistaPorDNI Response", response);
                            Gson gson = new Gson();
                            EspecialistaDTO especialistaDTO = gson.fromJson(response, EspecialistaDTO.class);
                            Log.d("Especialista", especialistaDTO.toString());
                            callBack.onSuccessEspecialista(especialistaDTO);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("GetEspecialistaPorDNI Error", "Error en la petición: " + error.toString());
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

    public static void getEspecialistasPorNombre(String nombre, Context contexto, EspecialistasCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + PreferenciasCompartidas.obtenerIP(contexto) + ":8080/especialistaPorNombre",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response == null || response.isEmpty()) {
                                Log.e("GetEspecialistasPorNombre Error: ", "Respuesta vacía o nula");
                                callBack.onError("No existen especialistas con el nombre proporcionado");
                            }
                            Gson gson = new Gson();
                            Type lista = new TypeToken<List<EspecialistaDTO>>() {}.getType();
                            List<EspecialistaDTO> array_especialistasDTO = gson.fromJson(response, lista);
                            callBack.onSuccessEspecialistas(array_especialistasDTO);
                        } catch (Exception e) {
                            Log.e("GetEspecialistasPorNombre Error", "Error parseando el JSON", e);
                            callBack.onError("Error parseando el JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetEspecialistasPorNombre Error", "Error en la petición: " + error.toString());
                        callBack.onError("Error en la petición: " + error.toString());
                    }
                })  {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("nombre", nombre);
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

    public static void actualizarEspecialistaPorDNI(String DNI, EspecialistaDTO especialistaActualizado, Context contexto, EspecialistasCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                "http://" + PreferenciasCompartidas.obtenerIP(contexto) + ":8080/updateCliente/" + DNI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response == null || response.isEmpty()) {
                            Log.e("ActualizarEspecialistaPorDNI Error: ", "Respuesta vacía o nula");
                            callBack.onError("No se ha podido modificar el especialista");
                        }

                        callBack.onSuccessModEspecialista(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ActualizarEspecialistaPorDNI Error", "Error en la petición: " + error.toString());
                        callBack.onError("Error en la petición: " + error.toString());
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("nombre", especialistaActualizado.getNombre());
                    jsonBody.put("apellidos", especialistaActualizado.getApellidos());
                    jsonBody.put("telefono", especialistaActualizado.getTelefono());
                    jsonBody.put("correo", especialistaActualizado.getCorreo());
                    jsonBody.put("residencia", especialistaActualizado.getResidencia());
                    jsonBody.put("sueldo", especialistaActualizado.getSueldo());
                    String requestBody = jsonBody.toString();
                    return requestBody.getBytes(StandardCharsets.UTF_8);
                } catch (JSONException e) {
                    Log.e("Body error: ", e.toString());
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
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

        // Agregar la petición a la cola de Volley
        RequestQueue queue = Volley.newRequestQueue(contexto);
        queue.add(stringRequest);
    }


}
