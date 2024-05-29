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
import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.Cuenta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteController {
    public static void getClientes(Context contexto, ClientesCallBack callBack) {
        RequestQueue queue = Volley.newRequestQueue(contexto);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://192.168.68.101:8080/clientes",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response == null || response.isEmpty()) {
                                Log.e("GetClientes Error: ", "Respuesta vacía o nula");
                                callBack.onError("No se han encontrado clientes");
                            }
                            Gson gson = new Gson();
                            Type lista = new TypeToken<List<Cliente>>() {}.getType();
                            List<Cliente> array_clientes = gson.fromJson(response, lista);
                            callBack.onSuccessClientes(array_clientes);
                        } catch (Exception e) {
                            Log.e("GetClientes Error", "Error parseando el JSON", e);
                            callBack.onError("Error parseando el JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetClientes Error", "Error en la petición: " + error.toString());
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

    public static void getClientePorDNI(String DNI, Context contexto, ClientesCallBack callBack) {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.68.101:8080/clientePorDni",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response == null || response.isEmpty()) {
                                Log.e("GetClientePorDNI Error", "Respuesta vacía o nula");
                                callBack.onError("No existe un cliente con el DNI proporcionado");
                                return;
                            }

                            Log.d("GetClientePorDNI Response", response);
                            Gson gson = new Gson();
                            Cliente cliente = gson.fromJson(response, Cliente.class);
                            Log.d("Cliente", cliente.toString());
                            callBack.onSuccess(cliente);
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

    public static void getClientesPorNombre(String nombre, Context contexto, ClientesCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.68.101:8080/clientePorNombre",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response == null || response.isEmpty()) {
                                Log.e("GetClientesPorNombre Error: ", "Respuesta vacía o nula");
                                callBack.onError("No existen clientes con el nombre proporcionado");
                            }
                            Gson gson = new Gson();
                            Type lista = new TypeToken<List<Cliente>>() {}.getType();
                            List<Cliente> array_clientes = gson.fromJson(response, lista);
                            callBack.onSuccessClientes(array_clientes);
                        } catch (Exception e) {
                            Log.e("GetClientesPorNombre Error", "Error parseando el JSON", e);
                            callBack.onError("Error parseando el JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetClientesPorNombre Error", "Error en la petición: " + error.toString());
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

    public static void actualizarClientePorDNI(String DNI, Cliente cliente, Context contexto, ClientesCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, "http://192.168.68.101:8080/updateCliente/" + DNI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response == null || response.isEmpty()) {
                            Log.e("ActualizarClientePorDNI Error: ", "Respuesta vacía o nula");
                            callBack.onError("No se ha podido modificar el cliente");
                        }

                        callBack.onSuccessModCliente(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ActualizarClientePorDNI Error", "Error en la petición: " + error.toString());
                        callBack.onError("Error en la petición: " + error.toString());
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("nombre", cliente.getNombre());
                    jsonBody.put("apellidos", cliente.getApellidos());
                    jsonBody.put("telefono", cliente.getTelefono());
                    jsonBody.put("correo", cliente.getCorreo());
                    jsonBody.put("residencia", cliente.getResidencia());
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
