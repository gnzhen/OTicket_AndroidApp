package com.example.gd.oticket.myrequest;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.gd.oticket.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by GD on 3/11/2018.
 */

public class MyRequest {
    private Context context;
    private RequestQueue queue;
    private String ip;
    private Toast toast;

    public MyRequest(Context context, RequestQueue queue, String ip) {
        this.context = context;
        this.queue = queue;
        this.ip = ip;
    }

    public void register(final String name, final String email, final String phone, final String password, final String passwordConfirm) {

        String url = ip + "registerMobileUser";

        Log.d("url", url);

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.d("My App volley response", response);

                        JSONObject jResponse =  new JSONObject(response);

                        if(jResponse.has("success")){
                            showToast(jResponse.get("success").toString());

                        } else {
                            Iterator<String> keys = jResponse.keys();
                            String str_Name = keys.next();
                            JSONArray value = jResponse.getJSONArray(str_Name);
                            showToast(value.get(0).toString());
                        }

                    } catch (Throwable t) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Volley Error", error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("phone", phone);
                    map.put("password", password);
                    map.put("password_confirmation", passwordConfirm);

                    return map;
                }
            };

            VolleySingleton.getInstance(context).addToRequestQueue(request);

        }

        public void showToast(String text){
            toast = Toast.makeText(context ,text, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 150);
            toast.show();
        }
}
