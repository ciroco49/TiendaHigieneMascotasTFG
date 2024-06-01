package com.example.tiendahigienemascotas.Controladores;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.tiendahigienemascotas.CallBacks.ClientesCallBack;
import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.CuentaDTO;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.google.gson.Gson;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class CuentaController {

    public static void login(String correo, Context contexto, LoginCallBack callBack) {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "http://" + PreferenciasCompartidas.obtenerIP(contexto) + ":8080/login",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response == null || response.isEmpty()) {
                                Log.e("Login Error", "Respuesta vacía o nula");
                                callBack.onError("No existe una cuenta con dicho correo");
                                return;
                            }

                            Log.d("Login Response", response);
                            Gson gson = new Gson();
                            CuentaDTO cuentaDTO = gson.fromJson(response, CuentaDTO.class);
                            Log.d("Cuenta", cuentaDTO.toString());
                            callBack.onSuccess(cuentaDTO);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Login Error", "Error en la petición: " + error.toString());
                    callBack.onError(error.toString());
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("correo", correo);
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

    public static void registrar(String correo, String contrasenha, Context contexto, LoginCallBack callBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + PreferenciasCompartidas.obtenerIP(contexto) + ":8080/saveCuentas",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            if(response == null || response.isEmpty()) {
                                Log.e("Registrar Error: ", "Respuesta vacía o nula");
                                callBack.onError("No se ha podido registrar la cuenta");
                            }

                            callBack.onSuccessRegistro(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Registrar Error", "Error en la petición: " + error.toString());
                        callBack.onError("Error en la petición: " + error.toString());
                    }
                })  {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("correo", correo);
                    jsonBody.put("contrasenha", contrasenha);
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

    public static void existeCuentaLoggeada(Context contexto, LoginCallBack callBack) {

        String correoLoggeado = PreferenciasCompartidas.obtenerCorreoDesencriptado(contexto);

        if(correoLoggeado != null) {
            login(correoLoggeado, contexto, new LoginCallBack() {
                @Override
                public void onSuccess(CuentaDTO cuentaDTO) {
                    //Si existe la cuenta, devuelvo true al callBack pasado como parámetro desde la Activity
                    callBack.existeCuentaLoggeada(true);
                }

                @Override
                public void onSuccessRegistro(String mensaje) {}

                @Override
                public void onSuccessModCuentaImagen(String mensaje) {}

                @Override
                public void existeCuentaLoggeada(boolean existe) {}

                @Override
                public void onError(String mensaje) {
                    //Si la cuenta no existe, devuelvo false al callBack pasado como parámetro desde la Activity
                    PreferenciasCompartidas.limpiarPreferenciasCompartidasLogin(contexto);
                    callBack.existeCuentaLoggeada(false);
                }
            });
        } else {
            callBack.existeCuentaLoggeada(false);
        }

    }

    public static void actualizarCuentaPorCorreo(String correo, CuentaDTO cuentaDTO, Context contexto, LoginCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                "http://" + PreferenciasCompartidas.obtenerIP(contexto) + ":8080/updateCuenta/" + correo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response == null || response.isEmpty()) {
                            Log.e("ActualizarCuentaPorCorreo Error: ", "Respuesta vacía o nula");
                            callBack.onError("No existe la cuenta a modificar");
                        }

                        callBack.onSuccessModCuentaImagen(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ActualizarCuentaPorCorreo Error", "Error en la petición: " + error.toString());
                        callBack.onError("Error en la petición: " + error.toString());
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("contrasenha", cuentaDTO.getContrasenha());
                    jsonBody.put("imagen", cuentaDTO.getImagen());
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
