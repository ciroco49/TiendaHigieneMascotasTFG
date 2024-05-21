package com.example.tiendahigienemascotas.Controladores;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Modelos.Cuenta;
import com.google.gson.Gson;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CuentaController {

    public static void login(String correo, String contrasenha, Context contexto, LoginCallBack callBack) {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.68.101:8080/login",
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
                            Cuenta cuenta = gson.fromJson(response, Cuenta.class);
                            Log.d("Cuenta", cuenta.toString());
                            callBack.onSuccess(cuenta);
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
            };

            RequestQueue queue = Volley.newRequestQueue(contexto);
            queue.add(stringRequest);

        } catch (Exception ex) {
            Log.d("Error login: ", ex.getMessage());
        }
    }
}
